package com.drelis.homework.schema.dataloaders

import com.drelis.homework.schema.models.Album
import com.drelis.homework.schema.models.Photo
import com.expediagroup.graphql.dataloader.KotlinDataLoader
import kotlinx.coroutines.runBlocking
import org.dataloader.DataLoaderFactory
import java.util.concurrent.CompletableFuture

val AlbumPhotoDataLoader = object : KotlinDataLoader<Int, List<Photo>> {
    override val dataLoaderName = "BATCH_PHOTO_LOADER"
    override fun getDataLoader() = DataLoaderFactory.newDataLoader<Int, List<Photo>> { ids ->
        CompletableFuture.supplyAsync {
            runBlocking { ids.map { id -> Album.getPhotos(id) } }
        }
    }
}