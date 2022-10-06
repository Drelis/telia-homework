package com.drelis.homework

import com.drelis.homework.schema.dataloaders.*
import com.expediagroup.graphql.dataloader.KotlinDataLoaderRegistryFactory
import com.expediagroup.graphql.server.execution.GraphQLRequestHandler
import com.expediagroup.graphql.server.execution.GraphQLServer
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.server.request.*

class KtorGraphQLServer(
    requestParser: KtorGraphQLRequestParser,
    contextFactory: KtorGraphQLContextFactory,
    requestHandler: GraphQLRequestHandler
) : GraphQLServer<ApplicationRequest>(requestParser, contextFactory, requestHandler)

fun getGraphQLServer(mapper: ObjectMapper): KtorGraphQLServer {
    val dataLoaderRegistryFactory = KotlinDataLoaderRegistryFactory(
        PostDataLoader, UserDataLoader, AlbumDataLoader, AlbumPhotoDataLoader, PostCommentsDataLoader,
        UserPostsDataLoader, UserAlbumsDataLoader, UserToDosDataLoader
    )
    val requestParser = KtorGraphQLRequestParser(mapper)
    val graphQL = getGraphQLObject()
    val contextFactory = KtorGraphQLContextFactory()
    val requestHandler = GraphQLRequestHandler(graphQL, dataLoaderRegistryFactory)

    return KtorGraphQLServer(requestParser, contextFactory, requestHandler)
}