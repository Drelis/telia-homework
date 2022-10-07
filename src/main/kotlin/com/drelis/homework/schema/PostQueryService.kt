package com.drelis.homework.schema

import com.drelis.homework.schema.models.Post
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query

@Suppress("unused")
class PostQueryService : Query {
    @GraphQLDescription("Return list of posts based on filter parameter")
    suspend fun postQuery(filter: PostSearchParameters?) = Post.search(filter)

    @GraphQLDescription("Return post by given id")
    suspend fun postGet(id: Int) = Post.getById(id)
}

data class PostSearchParameters(
    val id: Int?,
    val name: String?,
    val email: String?,
    val body: String?,
    val postId: Int?
)