package com.drelis.homework.schema.dataloaders

import com.drelis.homework.schema.models.Album
import com.drelis.homework.schema.models.Post
import com.expediagroup.graphql.dataloader.KotlinDataLoader
import kotlinx.coroutines.runBlocking
import org.dataloader.DataLoaderFactory
import java.util.concurrent.CompletableFuture

val PostDataLoader = object : KotlinDataLoader<Int, Post> {
    override val dataLoaderName = "POST_LOADER"
    override fun getDataLoader() = DataLoaderFactory.newDataLoader<Int, Post> { ids ->
        CompletableFuture.supplyAsync {
            runBlocking { ids.map { id -> Post.getById(id) } }
        }
    }
}