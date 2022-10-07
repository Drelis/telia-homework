package com.drelis.homework.schema

import com.drelis.homework.schema.models.Comment
import com.drelis.homework.schema.models.Photo
import com.drelis.homework.schema.models.Post
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query

@Suppress("unused")
class PhotoQueryService : Query {
    @GraphQLDescription("Return list of photo based on filter parameter")
    suspend fun photoQuery(filter: PhotoSearchParameters) = Photo.search(filter)

    @GraphQLDescription("Return photo by given id")
    suspend fun photoGet(id: Int) = Photo.getById(id)
}

data class PhotoSearchParameters(
    val id: Int?,
    val title: String?,
    val url: String?,
    val thumbnailUr: String?,
    val albumId: Int?
)