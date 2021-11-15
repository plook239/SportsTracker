package com.gmail.lookpj2.sportstracker.data.remote.response

import com.gmail.lookpj2.sportstracker.data.remote.model.TeamModel

data class GetTeamsResponse(
    val teams: List<TeamModel>
)
