package com.maykmenezes.desafiosicred.features.eventsList.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventItemResponse(

    @field:SerializedName("date")
    val date: Long? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("price")
    val price: Double? = null,

    @field:SerializedName("latitude")
    val latitude: Double? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("longitude")
    val longitude: Double? = null): Parcelable