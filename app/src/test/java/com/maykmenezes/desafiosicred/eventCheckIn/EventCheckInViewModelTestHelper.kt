package com.maykmenezes.desafiosicred.eventCheckIn

import com.maykmenezes.desafiosicred.features.eventCheckIn.model.request.CheckInRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object EventCheckInViewModelTestHelper {

    const val fakeSuccessCheckIn = "CheckIn realizado com sucesso."

    val successResponse: Response<String> = Response.success(fakeSuccessCheckIn)

    val checkInRequest = CheckInRequest(
            name = "logan",
            email = "logan@gmail.com",
            eventId = "1")

    val unknownErrorResponse: Response<String> = Response.error(500, "".toResponseBody("".toMediaTypeOrNull()))
}