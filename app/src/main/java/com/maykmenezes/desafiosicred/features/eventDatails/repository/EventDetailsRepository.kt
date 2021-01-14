package com.maykmenezes.desafiosicred.features.eventDatails.repository

import com.maykmenezes.desafiosicred.features.eventDatails.model.response.EventDetailsResponse
import retrofit2.Response

interface EventDetailsRepository {
    suspend fun fetchEvent(eventId: String): Response<EventDetailsResponse>
}