package com.maykmenezes.desafiosicred.features.eventsList.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maykmenezes.desafiosicred.features.eventsList.model.response.EventItemResponse
import com.maykmenezes.desafiosicred.features.eventsList.repository.EventsListRepository
import com.maykmenezes.desafiosicred.util.ui.ScreenBehavior.CommonScreenBehavior.loading
import com.maykmenezes.desafiosicred.util.ui.ScreenBehavior.CommonScreenBehavior.success
import com.maykmenezes.desafiosicred.util.ui.ScreenBehavior.CommonScreenBehavior.error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class EventsListViewModel(private val repository: EventsListRepository) : ViewModel() {

    val eventsListLiveData = MutableLiveData<List<EventItemResponse>>()
    val screenLiveData = MutableLiveData<Triple<Int, Int, Int>>()

    fun fetchEvents() {
        if(eventsListLiveData.value == null) {
            screenLiveData.postValue(loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repository.fetchEvents()
                    if(response.isSuccessful) {
                        eventsListLiveData.postValue(response.body())
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