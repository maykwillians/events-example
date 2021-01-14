package com.maykmenezes.desafiosicred.features.eventCheckIn.service

import com.maykmenezes.desafiosicred.features.eventCheckIn.model.request.CheckInRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

private const val CHECKIN_END_POINT = "checkin"

interface EventCheckInService {
    @POST(CHECKIN_END_POINT)
    suspend fun checkIn(@Body checkInRequest: CheckInRequest): Response<String>
}