package com.maykmenezes.desafiosicred.features.eventsList.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maykmenezes.desafiosicred.features.eventsList.di.EventsListModule.eventsListModule
import com.maykmenezes.desafiosicred.features.eventsList.model.response.EventItemResponse
import com.maykmenezes.desafiosicred.features.eventsList.repository.EventsListRepository
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

class EventsListViewModel(
        private val repository: EventsListRepository,
        private val coroutineDispatcher: CoroutineDispatcher) : ViewModel() {

    val eventsListLiveData = MutableLiveData<List<EventItemResponse>>()
    val screenLiveData = MutableLiveData<ScreenStatus>()

    fun fetchEvents() {
        if(eventsListLiveData.value == null) {
            screenLiveData.postValue(loading)
            viewModelScope.launch(coroutineDispatcher) {
                try {
                    val response = repository.fetchEvents()
                    if(response.isSuccessful) {
                        eventsListLiveData.postValue(response.body())
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
        unloadKoinModules(eventsListModule)
    }
}