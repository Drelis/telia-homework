package com.drelis.homework.schema.models

import com.drelis.homework.schema.CommentSearchParameters
import com.drelis.homework.schema.GraphQLUtil
import com.drelis.homework.schema.UserSearchParameters
import com.drelis.homework.schema.dataloaders.AlbumDataLoader
import com.drelis.homework.schema.dataloaders.PostDataLoader
import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import graphql.schema.DataFetchingEnvironment
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.util.concurrent.CompletableFuture

data class Comment(
    val id: Int,
    val name: String? = null,
    val email: String? = null,
    val body: String? = null,
    val postId: Int? = null
)
{
    fun post(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<Post?> {
        return if (postId != null) {
            dataFetchingEnvironment.getValueFromDataLoader(PostDataLoader.dataLoaderName, postId)
        } else CompletableFuture.completedFuture(null)
    }

    @Suppress("unused")
    companion object {
        private var client = HttpClient(CIO)

        suspend fun search(params: CommentSearchParameters?): List<Comment> {
            val commentUrl = GraphQLUtil.getCommentUrl(params)
            val response = client.get(commentUrl)
            val typeToken = object : TypeToken<List<Comment>>() {}.type
            return Gson().fromJson(response.bodyAsText(), typeToken)
        }

        suspend fun getById(id: Int): Comment {
            val response = client.get("https://jsonplaceholder.typicode.com/comments/$id")
            return Gson().fromJson(response.bodyAsText(), Comment::class.java)
        }
    }
}
