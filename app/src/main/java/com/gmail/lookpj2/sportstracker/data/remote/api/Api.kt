package com.gmail.lookpj2.sportstracker.data.remote.api

import com.gmail.lookpj2.sportstracker.data.remote.response.GetPastEventsResponse
import com.gmail.lookpj2.sportstracker.data.remote.response.GetTeamsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("searchteams.php")
    fun getAllTeams(
        @Query("t") team: String
    ): Call<GetTeamsResponse>

    @GET("eventslast.php")
    fun getTeamsPastFiveHomeGames(
        @Query("id") teamId: String
    ): Call<GetPastEventsResponse>
}