package com.drelis.homework.schema

import com.drelis.homework.schema.models.Post
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query

class PostQueryService : Query {
    @GraphQLDescription("Return list of posts based on PostSearchParameter options")
    @Suppress("unused")

    suspend fun postQuery(filter: PostSearchParameters?) = Post.search(filter)

    suspend fun postGet(id: Int) = Post.getById(id)
}

data class PostSearchParameters(val ids: List<Int>)