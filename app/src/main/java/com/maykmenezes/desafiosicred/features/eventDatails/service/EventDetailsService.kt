package com.maykmenezes.desafiosicred.features.eventDatails.service

import com.maykmenezes.desafiosicred.features.eventDatails.model.response.EventDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

private const val EVENT_DETAILS_END_POINT = "events/{id}"

interface EventDetailsService {
    @GET(EVENT_DETAILS_END_POINT)
    suspend fun fetchEvent(@Path("id") id: String) : Response<EventDetailsResponse>
}