package com.maykmenezes.desafiosicred.features.eventCheckIn.repository

import com.maykmenezes.desafiosicred.features.eventCheckIn.model.request.CheckInRequest
import com.maykmenezes.desafiosicred.features.eventCheckIn.service.EventCheckInService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class EventCheckInRepositoryImpl(private val service: EventCheckInService) : EventCheckInRepository {

    override suspend fun checkIn(checkInRequest: CheckInRequest): Response<String> = withContext(Dispatchers.IO) {
        service.checkIn(checkInRequest)
    }
}