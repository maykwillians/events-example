package com.maykmenezes.desafiosicred.features.eventCheckIn.repository

import com.maykmenezes.desafiosicred.features.eventCheckIn.model.request.CheckInRequest
import retrofit2.Response

interface EventCheckInRepository {
    suspend fun checkIn(checkInRequest: CheckInRequest): Response<String>
}