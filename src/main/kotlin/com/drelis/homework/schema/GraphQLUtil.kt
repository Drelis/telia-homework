package com.drelis.homework.schema

import com.google.gson.*

object GraphQLUtil {
    private val GSON_MAPPER = GsonBuilder()
        .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
        .create()

    private const val resourceUrl: String = "https://jsonplaceholder.typicode.com";

    fun getPostUrl(searchParams: PostSearchParameters?) : String {
        val searchString = paramsToSearchString(searchParams)
        return "$resourceUrl/posts$searchString"
    }

    fun getCommentUrl(searchParams: CommentSearchParameters?) : String {
        val searchString = paramsToSearchString(searchParams)
        return "$resourceUrl/comments$searchString"
    }

    fun getAlbumUrl(searchParams: AlbumSearchParameters?) : String {
        val searchString = paramsToSearchString(searchParams)
        return "$resourceUrl/albums$searchString"
    }

    fun getPhotoUrl(searchParams: PhotoSearchParameters?) : String {
        val searchString = paramsToSearchString(searchParams)
        return "$resourceUrl/photos$searchString"
    }

    fun getToDoUrl(searchParams: ToDoSearchParameters?) : String {
        val searchString = paramsToSearchString(searchParams)
        return "$resourceUrl/todos$searchString"
    }

    fun getUserUrl(searchParams: UserSearchParameters?) : String {
        // User has nested Objects convert to with xxx.yyy
        val company = paramsToSearchString(searchParams?.company, "", "company.")
        var address = company
        if (searchParams?.address != null) {
            val geo = paramsToSearchString(searchParams.address.geo, address, "address.geo.")
            address =  paramsToSearchString(searchParams.address.copy(geo = null), geo, "address.")
        }
        val searchString = paramsToSearchString(searchParams?.copy(address = null, company = null), address)
        return "$resourceUrl/users$searchString"
    }

    private fun paramsToSearchString(searchParams: Any?, initial: String?  = "", prefix: String? = ""): String {
        var searchString = initial  ?: ""
        if (searchParams != null) {
            GSON_MAPPER.fromJson(GSON_MAPPER.toJson(searchParams), Map::class.java).forEach { entry ->
                if (entry.value != null && entry.value.toString().isNotEmpty()) searchString += "&$prefix${entry.key}=${entry.value}"
            }
            if (searchString.isNotEmpty()) searchString = searchString.replaceFirst("&", "?")
        }
        return searchString
    }
}

