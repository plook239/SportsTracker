package com.gmail.lookpj2.sportstracker.data

import com.gmail.lookpj2.sportstracker.data.remote.api.Api
import com.gmail.lookpj2.sportstracker.data.remote.model.PastEventModel
import com.gmail.lookpj2.sportstracker.data.remote.model.TeamModel
import com.gmail.lookpj2.sportstracker.data.remote.response.GetPastEventsResponse
import com.gmail.lookpj2.sportstracker.data.remote.response.GetTeamsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Repository {
    private val api: Api

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thesportsdb.com/api/v1/json/2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }

    fun getSearchedTeams(
        team: String,
        onSuccess: (teams: List<TeamModel>) -> Unit?,
        onError: () -> Unit
    ) {
        api.getAllTeams(team = team)
            .enqueue(object : Callback<GetTeamsResponse> {
                override fun onResponse(
                    call: Call<GetTeamsResponse>,
                    response: Response<GetTeamsResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            try {
                                onSuccess.invoke(responseBody.teams)
                            } catch (e: Exception) {
                                onError.invoke()
                            }
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<GetTeamsResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getTeamsPastFiveEvents(
        teamId: String,
        onSuccess: (pastEvents: List<PastEventModel>) -> Unit,
        onError: () -> Unit
    ) {
        api.getTeamsPastFiveHomeGames(teamId = teamId)
            .enqueue(object : Callback<GetPastEventsResponse> {
                override fun onResponse(
                    call: Call<GetPastEventsResponse>,
                    response: Response<GetPastEventsResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.results)
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<GetPastEventsResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
}