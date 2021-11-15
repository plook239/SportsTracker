package com.gmail.lookpj2.sportstracker.data.remote.model

import com.google.gson.annotations.SerializedName

data class PastEventModel(
    @SerializedName("strEvent") val eventName: String,
    @SerializedName("strHomeTeam") val homeTeam: String,
    @SerializedName("strAwayTeam") val awayTeam: String,
    @SerializedName("intHomeScore") val homeTeamScore: Int,
    @SerializedName("intAwayScore") val awayTeamScore: Int
)
