package com.maykmenezes.desafiosicred.features.eventDatails.repository

import com.maykmenezes.desafiosicred.features.eventDatails.model.response.EventDetailsResponse
import com.maykmenezes.desafiosicred.features.eventDatails.service.EventDetailsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class EventDetailsRepositoryImpl(private val service: EventDetailsService) : EventDetailsRepository {

    override suspend fun fetchEvent(eventId: String): Response<EventDetailsResponse> = withContext(Dispatchers.IO) {
        service.fetchEvent(eventId)
    }
}