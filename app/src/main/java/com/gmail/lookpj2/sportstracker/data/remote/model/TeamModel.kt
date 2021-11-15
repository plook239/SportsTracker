package com.gmail.lookpj2.sportstracker.data.remote.model

import com.google.gson.annotations.SerializedName

data class TeamModel(
    @SerializedName("idTeam") val teamId: String,
    @SerializedName("strTeam") val teamName: String,
    @SerializedName("strTeamBadge") val badgeUrl: String
)