package com.drelis.homework.schema.models

import com.drelis.homework.schema.AlbumSearchParameters
import com.drelis.homework.schema.GraphQLUtil
import com.drelis.homework.schema.dataloaders.AlbumPhotoDataLoader
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

data class Album(
    val id: Int,
    val title: String? = null,
    val userId: Int? = null
)
{
    fun user(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<User?> {
        return if (userId != null) {
            dataFetchingEnvironment.getValueFromDataLoader(UserDataLoader.dataLoaderName, userId)
        } else CompletableFuture.completedFuture(null)
    }

    fun photos(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<List<Photo>> {
        return dataFetchingEnvironment.getValueFromDataLoader(AlbumPhotoDataLoader.dataLoaderName, id)
    }

    @Suppress("unused")
    companion object {
        private var client = HttpClient(CIO)

        suspend fun search(params: AlbumSearchParameters?): List<Album> {
            val response = client.get(GraphQLUtil.getAlbumUrl(params))
            val typeToken = object : TypeToken<List<Album>>() {}.type
            return Gson().fromJson(response.bodyAsText(), typeToken)
        }

        suspend fun getById(id: Int): Album {
            val response = client.get("${GraphQLUtil.getAlbumUrl(null)}/$id")
            return Gson().fromJson(response.bodyAsText(), Album::class.java)
        }

        suspend fun getPhotos(id: Int): List<Photo> {
            val response = client.get( "${GraphQLUtil.getAlbumUrl(null)}/$id/photos")
            val typeToken = object : TypeToken<List<Photo>>() {}.type
            return Gson().fromJson(response.bodyAsText(), typeToken)
        }
    }
}