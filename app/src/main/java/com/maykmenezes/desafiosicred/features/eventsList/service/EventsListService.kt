package com.maykmenezes.desafiosicred.features.eventsList.service

import com.maykmenezes.desafiosicred.features.eventsList.model.response.EventItemResponse
import retrofit2.Response
import retrofit2.http.GET

private const val EVENTS_LIST_END_POINT = "events"

interface EventsListService {
    @GET(EVENTS_LIST_END_POINT)
    suspend fun fetchEvents() : Response<List<EventItemResponse>>
}