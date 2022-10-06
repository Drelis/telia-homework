package com.drelis.homework.schema.models

import com.drelis.homework.schema.GraphQLUtil
import com.drelis.homework.schema.PhotoSearchParameters
import com.drelis.homework.schema.dataloaders.AlbumDataLoader
import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import graphql.schema.DataFetchingEnvironment
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.util.concurrent.CompletableFuture

data class Photo(
    val id: Int,
    val title: String? = null,
    val url: String? = null,
    val thumbnailUrl: String? = null,
    val albumId: Int? = null
)
{
    fun album(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<Album?> {
        return if (albumId != null) {
            dataFetchingEnvironment.getValueFromDataLoader(AlbumDataLoader.dataLoaderName, albumId)
        } else CompletableFuture.completedFuture(null)
    }

    @Suppress("unused")
    companion object {
        private var client = HttpClient(CIO)

        suspend fun search(params: PhotoSearchParameters?): List<Photo> {
            val response = client.get(GraphQLUtil.getPhotoUrl(params))
            val typeToken = object : TypeToken<List<Photo>>() {}.type
            return Gson().fromJson(response.bodyAsText(), typeToken)
        }

        suspend fun getById(id: Int): Photo {
            val response = client.get("${GraphQLUtil.getPhotoUrl(null)}/$id")
            return Gson().fromJson(response.bodyAsText(), Photo::class.java)
        }
    }
}