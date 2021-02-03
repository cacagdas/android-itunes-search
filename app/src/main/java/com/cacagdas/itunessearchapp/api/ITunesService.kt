package com.cacagdas.itunessearchapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * REST API access points
 */
interface ITunesService {

    @GET("search")
    fun search(
            @Query("term") query: String,
            @Query("media") mediaType: String,
    ): Call<ApiResponse>
}
