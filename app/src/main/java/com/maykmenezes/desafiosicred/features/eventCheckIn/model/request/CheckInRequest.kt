package com.maykmenezes.desafiosicred.features.eventCheckIn.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckInRequest(

	@field:SerializedName("eventId")
	val eventId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null
): Parcelable