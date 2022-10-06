package com.drelis.homework.schema

import com.drelis.homework.schema.models.Album
import com.drelis.homework.schema.models.Post
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query

@Suppress("unused")
class AlbumQueryService : Query {
    @GraphQLDescription("Return list of posts based on PostSearchParameter options")
    suspend fun albumQuery(filter: AlbumSearchParameters?) = Album.search(filter)

    @GraphQLDescription("Return list of posts based on PostSearchParameter options")
    suspend fun albumGet(id: Int) = Album.getById(id)
}

data class AlbumSearchParameters(
    val id: Int?,
    val userId: Int?,
    val title: String?,
)