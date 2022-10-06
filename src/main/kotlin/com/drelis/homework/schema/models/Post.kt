package com.drelis.homework.schema.models

import com.drelis.homework.schema.GraphQLUtil
import com.drelis.homework.schema.PostSearchParameters
import com.drelis.homework.schema.dataloaders.PostCommentsDataLoader
import com.drelis.homework.schema.dataloaders.UserDataLoader
import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import graphql.schema.DataFetchingEnvironment
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.util.concurrent.CompletableFuture

data class Post (
    val id: Int,
    val title: String? = null,
    val body: String? = null,
    val userId: Int? = null
) {
    fun user(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<User?> {
        return if (userId != null) {
            dataFetchingEnvironment.getValueFromDataLoader(UserDataLoader.dataLoaderName, userId)
        } else CompletableFuture.completedFuture(null)
    }

    fun comments(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<List<Comment>> {
        return dataFetchingEnvironment.getValueFromDataLoader(PostCommentsDataLoader.dataLoaderName, id)
    }

    companion object {
        private var client = HttpClient(CIO)

        suspend fun search(filter: PostSearchParameters?): List<Post> {
            val response = client.get(GraphQLUtil.getPostUrl(filter))
            val typeToken = object : TypeToken<List<Post>>() {}.type
            return Gson().fromJson(response.bodyAsText(), typeToken)
        }

        suspend fun getById(id: Int): Post {
            val response = client.get( "${GraphQLUtil.getPostUrl(null)}/$id")
            val typeToken = object : TypeToken<Post>() {}.type
            return Gson().fromJson(response.bodyAsText(), typeToken)
        }

        suspend fun getComments(id: Int): List<Comment> {
            val response = client.get( "${GraphQLUtil.getPostUrl(null)}/$id/comments")
            val typeToken = object : TypeToken<List<Comment>>() {}.type
            return Gson().fromJson(response.bodyAsText(), typeToken)
        }
    }
}