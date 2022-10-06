package com.drelis.homework.schema.dataloaders

import com.drelis.homework.schema.models.Album
import com.drelis.homework.schema.models.Post
import com.drelis.homework.schema.models.User
import com.expediagroup.graphql.dataloader.KotlinDataLoader
import kotlinx.coroutines.runBlocking
import org.dataloader.DataLoaderFactory
import java.util.concurrent.CompletableFuture

val AlbumDataLoader = object : KotlinDataLoader<Int, Album?> {
    override val dataLoaderName = "ALBUM_LOADER"
    override fun getDataLoader() = DataLoaderFactory.newDataLoader<Int, Album> { ids ->
        CompletableFuture.supplyAsync {
            runBlocking { ids.map { id -> Album.getById(id) } }
        }
    }
}