package com.drelis.homework.schema

import com.drelis.homework.schema.models.Comment
import com.drelis.homework.schema.models.Photo
import com.drelis.homework.schema.models.Post
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query

@Suppress("unused")
class PhotoQueryService : Query {
    @GraphQLDescription("Return list of posts based on PostSearchParameter options")
    suspend fun photoQuery(filter: PhotoSearchParameters) = Photo.search(filter)

    suspend fun photoGet(id: Int) = Photo.getById(id)
}

data class PhotoSearchParameters(
    val id: Int?,
    val albumId: Int?
)