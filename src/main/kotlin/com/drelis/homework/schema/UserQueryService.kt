package com.drelis.homework.schema

import com.drelis.homework.schema.models.Address
import com.drelis.homework.schema.models.Company
import com.drelis.homework.schema.models.User
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query

@Suppress("unused")
class UserQueryService : Query {
    @GraphQLDescription("Return list of users based on filter")
    suspend fun userQuery(filter: UserSearchParameters? = null) = User.search(filter)

    @GraphQLDescription("Return user by given id")
    suspend fun userGet(id: Int) = User.getById(id)
}

data class UserSearchParameters(
    val id: Int? = null,
    val name: String? = "",
    val username: String? = "",
    val email: String? = "",
    val phone: String? = "",
    val website: String? = "",
    val company: Company? = null,
    val address: Address? = null
)