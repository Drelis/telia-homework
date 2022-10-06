package com.drelis.homework

import com.expediagroup.graphql.generator.extensions.print
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.engine.*
import io.ktor.server.routing.*
import io.ktor.server.netty.*

fun main() {
	val mapper = jacksonObjectMapper()
	val ktorGraphQLServer = getGraphQLServer(mapper)
	embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
		routing {
			post("/graphql") {
				val result = ktorGraphQLServer.execute(this.call.request)

				if (result != null) {
					val json = mapper.writeValueAsString(result)
					call.respondText(json, ContentType.Application.Json, HttpStatusCode.OK)
				} else {
					call.respond(HttpStatusCode.BadRequest, "Invalid request")
				}
			}

			get("/sdl") {
				call.respondText(graphQLSchema.print())
			}

			get("playground") {
				call.respondText(buildPlaygroundHtml("graphql", "subscriptions"), ContentType.Text.Html)
			}
		}
	}.start(wait = true)
}

private fun buildPlaygroundHtml(graphQLEndpoint: String, subscriptionsEndpoint: String) =
	Application::class.java.classLoader.getResource("graphql-playground.html")?.readText()
		?.replace("\${graphQLEndpoint}", graphQLEndpoint)
		?.replace("\${subscriptionsEndpoint}", subscriptionsEndpoint)
		?: throw IllegalStateException("graphql-playground.html cannot be found in the classpath")
