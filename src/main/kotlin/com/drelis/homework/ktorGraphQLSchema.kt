package com.drelis.homework

import com.drelis.homework.schema.*
import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.scalars.IDValueUnboxer
import com.expediagroup.graphql.generator.toSchema
import graphql.GraphQL

private val config = SchemaGeneratorConfig(supportedPackages = listOf("com.drelis.homework"))
private val queries = listOf(
    TopLevelObject(PostQueryService()),
    TopLevelObject(CommentQueryService()),
    TopLevelObject(AlbumQueryService()),
    TopLevelObject(PhotoQueryService()),
    TopLevelObject(ToDoQueryService()),
    TopLevelObject(UserQueryService())
)

val graphQLSchema = toSchema(config, queries, emptyList())

fun getGraphQLObject(): GraphQL = GraphQL.newGraphQL(graphQLSchema)
    .valueUnboxer(IDValueUnboxer())
    .build()