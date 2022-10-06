package com.drelis.homework.schema.dataloaders

import com.drelis.homework.schema.models.*
import com.expediagroup.graphql.dataloader.KotlinDataLoader
import kotlinx.coroutines.runBlocking
import org.dataloader.DataLoaderFactory
import java.util.concurrent.CompletableFuture

val UserPostsDataLoader = object : KotlinDataLoader<Int, List<Post>> {
    override val dataLoaderName = "BATCH_POSTS_LOADER"
    override fun getDataLoader() = DataLoaderFactory.newDataLoader<Int, List<Post>> { ids ->
        CompletableFuture.supplyAsync {
            runBlocking { ids.map { id -> User.getPosts(id) } }
        }
    }
}

val UserAlbumsDataLoader = object : KotlinDataLoader<Int, List<Album>> {
    override val dataLoaderName = "BATCH_ALBUMS_LOADER"
    override fun getDataLoader() = DataLoaderFactory.newDataLoader<Int, List<Album>> { ids ->
        CompletableFuture.supplyAsync {
            runBlocking { ids.map { id -> User.getAlbums(id) } }
        }
    }
}

val UserToDosDataLoader = object : KotlinDataLoader<Int, List<ToDo>> {
    override val dataLoaderName = "BATCH_TODOS_LOADER"
    override fun getDataLoader() = DataLoaderFactory.newDataLoader<Int, List<ToDo>> { ids ->
        CompletableFuture.supplyAsync {
            runBlocking { ids.map { id -> User.getToDos(id) } }
        }
    }
}