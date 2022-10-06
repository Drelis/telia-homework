package com.drelis.homework.schema.dataloaders

import com.drelis.homework.schema.models.Comment
import com.drelis.homework.schema.models.Post
import com.expediagroup.graphql.dataloader.KotlinDataLoader
import kotlinx.coroutines.runBlocking
import org.dataloader.DataLoaderFactory
import java.util.concurrent.CompletableFuture

val PostCommentsDataLoader = object : KotlinDataLoader<Int, List<Comment>> {
    override val dataLoaderName = "BATCH_COMMENTS_LOADER"
    override fun getDataLoader() = DataLoaderFactory.newDataLoader<Int, List<Comment>> { ids ->
        CompletableFuture.supplyAsync {
            runBlocking { ids.map { id -> Post.getComments(id) } }
        }
    }
}