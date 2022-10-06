package com.drelis.homework.schema

import com.drelis.homework.schema.models.Comment
import com.drelis.homework.schema.models.Post
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query

@Suppress("unused")
class CommentQueryService : Query {
    @GraphQLDescription("Return list of comments based on filter parameter")
    suspend fun commentQuery(filter: CommentSearchParameters?) = Comment.search(filter)

    @GraphQLDescription("Return comment by a given id")
    suspend fun commentGet(id: Int) = Comment.getById(id)
}

data class CommentSearchParameters(
    val id: Int?,
    val postId: Int?,
    val name: String?,
    val email: String?,
    val body: String?
)