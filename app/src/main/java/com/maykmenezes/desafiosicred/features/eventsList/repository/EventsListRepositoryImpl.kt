package com.maykmenezes.desafiosicred.features.eventsList.repository

import com.maykmenezes.desafiosicred.features.eventsList.model.response.EventItemResponse
import com.maykmenezes.desafiosicred.features.eventsList.service.EventsListService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class EventsListRepositoryImpl(private val service: EventsListService) : EventsListRepository {

    override suspend fun fetchEvents(): Response<List<EventItemResponse>> = withContext(Dispatchers.IO) {
        service.fetchEvents()
    }
}