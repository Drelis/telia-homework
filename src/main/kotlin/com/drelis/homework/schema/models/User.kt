package com.drelis.homework.schema.models

import com.drelis.homework.schema.GraphQLUtil
import com.drelis.homework.schema.UserSearchParameters
import com.drelis.homework.schema.dataloaders.UserAlbumsDataLoader
import com.drelis.homework.schema.dataloaders.UserPostsDataLoader
import com.drelis.homework.schema.dataloaders.UserToDosDataLoader
import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import graphql.schema.DataFetchingEnvironment
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.util.concurrent.CompletableFuture

data class Geo(
    val lat: Double? = null,
    val lng: Double? = null
)

data class Address(
    val street: String? = null,
    val suite: String? = null,
    val city: String? = null,
    val zipcode: String? = null,
    val geo: Geo? = null
)

data class Company(
    val name: String? = null,
    val catchPhrase: String? = null,
    val bs: String? = null
)

@Suppress("unused")
data class User(
    val id: Int,
    val name: String? = null,
    val username: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val website: String? = null,
    val address: Address? = null,
    val company: Company? = null
)
{
    fun comments(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<List<Post>> {
        return dataFetchingEnvironment.getValueFromDataLoader(UserPostsDataLoader.dataLoaderName, id)
    }

    fun albums(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<List<Album>> {
        return dataFetchingEnvironment.getValueFromDataLoader(UserAlbumsDataLoader.dataLoaderName, id)
    }

    fun todos(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<List<ToDo>> {
        return dataFetchingEnvironment.getValueFromDataLoader(UserToDosDataLoader.dataLoaderName, id)
    }

    companion object {
        private var client = HttpClient(CIO)

        suspend fun search(param: UserSearchParameters?): List<User> {
            val response = client.get(GraphQLUtil.getUserUrl(param))
            val typeToken = object : TypeToken<List<User>>() {}.type
            return Gson().fromJson(response.bodyAsText(), typeToken)
        }

        suspend fun getById(id: Int): User? {
            val response = client.get( "${GraphQLUtil.getUserUrl(null)}/$id")
            return Gson().fromJson(response.bodyAsText(), User::class.java)
        }

        suspend fun getPosts(id: Int): List<Post> {
            val response = client.get( "${GraphQLUtil.getUserUrl(null)}/$id/posts")
            val typeToken = object : TypeToken<List<Post>>() {}.type
            return Gson().fromJson(response.bodyAsText(), typeToken)
        }

        suspend fun getAlbums(id: Int): List<Album> {
            val response = client.get( "${GraphQLUtil.getUserUrl(null)}/$id/albums")
            val typeToken = object : TypeToken<List<Album>>() {}.type
            return Gson().fromJson(response.bodyAsText(), typeToken)
        }

        suspend fun getToDos(id: Int): List<ToDo> {
            val response = client.get( "${GraphQLUtil.getUserUrl(null)}/$id/todos")
            val typeToken = object : TypeToken<List<ToDo>>() {}.type
            return Gson().fromJson(response.bodyAsText(), typeToken)
        }
    }
}