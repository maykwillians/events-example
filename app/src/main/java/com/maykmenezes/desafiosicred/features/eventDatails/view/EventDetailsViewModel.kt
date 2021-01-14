package com.maykmenezes.desafiosicred.features.eventDatails.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maykmenezes.desafiosicred.features.eventDatails.model.response.EventDetailsResponse
import com.maykmenezes.desafiosicred.features.eventDatails.repository.EventDetailsRepository
import com.maykmenezes.desafiosicred.util.ui.ScreenBehavior.CommonScreenBehavior.loading
import com.maykmenezes.desafiosicred.util.ui.ScreenBehavior.CommonScreenBehavior.success
import com.maykmenezes.desafiosicred.util.ui.ScreenBehavior.CommonScreenBehavior.error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class EventDetailsViewModel(private val repository: EventDetailsRepository) : ViewModel() {

    val eventDetailsLiveData = MutableLiveData<EventDetailsResponse>()
    val screenLiveData = MutableLiveData<Triple<Int, Int, Int>>()

    fun fetchEvent(eventId: String) {
        if(eventDetailsLiveData.value == null) {
            screenLiveData.postValue(loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repository.fetchEvent(eventId)
                    if(response.isSuccessful) {
                        eventDetailsLiveData.postValue(response.body())
                        screenLiveData.postValue(success)
                    } else {
                        screenLiveData.postValue(error)
                    }
                } catch(e: Exception) {
                    screenLiveData.postValue(error)
                }
            }
        }
    }
}