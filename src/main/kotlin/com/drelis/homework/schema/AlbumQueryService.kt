package com.drelis.homework.schema

import com.drelis.homework.schema.models.Album
import com.drelis.homework.schema.models.Post
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query

@Suppress("unused")
class AlbumQueryService : Query {
    @GraphQLDescription("Return list of abums based on filter parameter")
    suspend fun albumQuery(filter: AlbumSearchParameters?) = Album.search(filter)

    @GraphQLDescription("Return album by a given id")
    suspend fun albumGet(id: Int) = Album.getById(id)
}

data class AlbumSearchParameters(
    val id: Int?,
    val userId: Int?,
    val title: String?,
)