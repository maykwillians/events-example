package com.maykmenezes.desafiosicred.features.eventDatails.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maykmenezes.desafiosicred.features.eventDatails.di.EventDetailsModule.eventDetailsModule
import com.maykmenezes.desafiosicred.features.eventDatails.model.response.EventDetailsResponse
import com.maykmenezes.desafiosicred.features.eventDatails.repository.EventDetailsRepository
import com.maykmenezes.desafiosicred.util.ui.ScreenStatus
import com.maykmenezes.desafiosicred.util.ui.CustomScreenStates.loading
import com.maykmenezes.desafiosicred.util.ui.CustomScreenStates.success
import com.maykmenezes.desafiosicred.util.ui.CustomScreenStates.unknownError
import com.maykmenezes.desafiosicred.util.ui.CustomScreenStates.noConnectionError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.koin.core.context.unloadKoinModules
import java.io.IOException
import java.lang.Exception

class EventDetailsViewModel(
    private val repository: EventDetailsRepository,
    private val coroutineDispatcher: CoroutineDispatcher) : ViewModel() {

    val eventDetailsLiveData = MutableLiveData<EventDetailsResponse>()
    val screenLiveData = MutableLiveData<ScreenStatus>()

    fun fetchEvent(eventId: String) {
        if(eventDetailsLiveData.value == null) {
            screenLiveData.postValue(loading)
            viewModelScope.launch(coroutineDispatcher) {
                try {
                    val response = repository.fetchEvent(eventId)
                    if(response.isSuccessful) {
                        eventDetailsLiveData.postValue(response.body())
                        screenLiveData.postValue(success)
                    } else {
                        screenLiveData.postValue(unknownError)
                    }
                } catch(e: Exception) {
                    when(e) {
                        is IOException -> screenLiveData.postValue(noConnectionError)
                        else -> screenLiveData.postValue(unknownError)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        unloadKoinModules(eventDetailsModule)
    }
}