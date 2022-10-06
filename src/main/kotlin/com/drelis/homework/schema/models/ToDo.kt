package com.drelis.homework.schema.models

import com.drelis.homework.schema.GraphQLUtil
import com.drelis.homework.schema.ToDoSearchParameters
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

data class ToDo(
    val id: Int,
    val title: String? = null,
    val completed: Boolean? = null,
    val userId: Int? = null
)
{
    fun user(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<User?> {
        return if (userId != null) {
            dataFetchingEnvironment.getValueFromDataLoader(UserDataLoader.dataLoaderName, userId)
        } else CompletableFuture.completedFuture(null)
    }

    @Suppress("unused")
    companion object {
        private var client = HttpClient(CIO)

        suspend fun search(params: ToDoSearchParameters?): List<ToDo> {
            val response = client.get(GraphQLUtil.getToDoUrl(params))
            val typeToken = object : TypeToken<List<ToDo>>() {}.type
            return Gson().fromJson(response.bodyAsText(), typeToken)
        }

        suspend fun getById(id: Int): ToDo {
            val response = client.get("${GraphQLUtil.getToDoUrl(null)}/$id")
            return Gson().fromJson(response.bodyAsText(), ToDo::class.java)
        }
    }
}