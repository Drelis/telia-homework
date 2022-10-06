package com.drelis.homework.schema.dataloaders

import com.drelis.homework.schema.models.User
import com.expediagroup.graphql.dataloader.KotlinDataLoader
import kotlinx.coroutines.runBlocking
import org.dataloader.DataLoaderFactory
import java.util.concurrent.CompletableFuture

val UserDataLoader = object : KotlinDataLoader<Int, User?> {
    override val dataLoaderName = "USER_LOADER"
    override fun getDataLoader() = DataLoaderFactory.newDataLoader<Int, User?> { ids ->
        CompletableFuture.supplyAsync {
            runBlocking { ids.map { id -> User.getById(id) } }
        }
    }
}