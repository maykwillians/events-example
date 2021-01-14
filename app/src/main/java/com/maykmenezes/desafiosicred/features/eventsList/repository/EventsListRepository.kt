package com.maykmenezes.desafiosicred.features.eventsList.repository

import com.maykmenezes.desafiosicred.features.eventsList.model.response.EventItemResponse
import retrofit2.Response

interface EventsListRepository {
    suspend fun fetchEvents(): Response<List<EventItemResponse>>
}