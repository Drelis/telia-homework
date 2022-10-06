package com.drelis.homework.schema

import com.drelis.homework.schema.models.ToDo
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query

class ToDoQueryService : Query {
    @GraphQLDescription("Return list of posts based on PostSearchParameter options")
    @Suppress("unused")

    suspend fun toDoQuery(filter: ToDoSearchParameters) = ToDo.search(filter)

    suspend fun toDoGet(id: Int) = ToDo.getById(id)
}

data class ToDoSearchParameters(
    val id: Int?
)