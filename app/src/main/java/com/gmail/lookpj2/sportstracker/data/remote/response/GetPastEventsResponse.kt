package com.gmail.lookpj2.sportstracker.data.remote.response

import com.gmail.lookpj2.sportstracker.data.remote.model.PastEventModel

data class GetPastEventsResponse(
        val results: List<PastEventModel>
)
