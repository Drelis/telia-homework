package com.drelis.homework.schema

import com.drelis.homework.schema.models.ToDo
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query

@Suppress("unused")
class ToDoQueryService : Query {
    @GraphQLDescription("Return list of ToDo list based on filter parameter")
    suspend fun toDoQuery(filter: ToDoSearchParameters) = ToDo.search(filter)

    @GraphQLDescription("Return ToDo item by given id")
    suspend fun toDoGet(id: Int) = ToDo.getById(id)
}

data class ToDoSearchParameters(
    val id: Int?,
    val title: String?,
    val competed: Boolean?,
    val userId: Int?
)