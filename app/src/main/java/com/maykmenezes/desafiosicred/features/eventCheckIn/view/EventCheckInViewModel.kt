package com.maykmenezes.desafiosicred.features.eventCheckIn.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maykmenezes.desafiosicred.features.eventCheckIn.di.EventCheckInModule.eventCheckInModule
import com.maykmenezes.desafiosicred.features.eventCheckIn.model.request.CheckInRequest
import com.maykmenezes.desafiosicred.features.eventCheckIn.repository.EventCheckInRepository
import com.maykmenezes.desafiosicred.util.data.emailIsInvalid
import com.maykmenezes.desafiosicred.util.data.nameIsInvalid
import com.maykmenezes.desafiosicred.util.ui.ScreenStatus
import com.maykmenezes.desafiosicred.util.ui.CustomScreenStates.loading
import com.maykmenezes.desafiosicred.util.ui.CustomScreenStates.success
import com.maykmenezes.desafiosicred.util.ui.CustomScreenStates.unknownError
import com.maykmenezes.desafiosicred.util.ui.CustomScreenStates.noConnectionError
import com.maykmenezes.desafiosicred.util.ui.Strings.emailIsInvalid
import com.maykmenezes.desafiosicred.util.ui.Strings.insertYourEmail
import com.maykmenezes.desafiosicred.util.ui.Strings.insertYourName
import com.maykmenezes.desafiosicred.util.ui.Strings.nameIsInvalid
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.koin.core.context.unloadKoinModules
import java.io.IOException
import java.lang.Exception

class EventCheckInViewModel(
    private val repository: EventCheckInRepository,
    private val coroutineDispatcher: CoroutineDispatcher) : ViewModel() {

    val eventCheckInLiveData = MutableLiveData<String>()
    val screenLiveData = MutableLiveData<ScreenStatus>()
    val inputNameErrorMessageLiveData = MutableLiveData<Int>()
    val inputEmailErrorMessageLiveData = MutableLiveData<Int>()

    fun checkIn(eventId: String, name: String, email: String) {
        if(inputsIsValid(name, email)) {
            screenLiveData.postValue(loading)
            viewModelScope.launch(coroutineDispatcher) {
                try {
                    val response = repository.checkIn(CheckInRequest(eventId, name, email))
                    if(response.isSuccessful) {
                        eventCheckInLiveData.postValue(response.body())
                        screenLiveData.postValue(success)
                    } else {
                        eventCheckInLiveData.postValue(null)
                        screenLiveData.postValue(unknownError)
                    }
                } catch(e: Exception) {
                    when(e) {
                        is IOException -> {
                            eventCheckInLiveData.postValue(null)
                            screenLiveData.postValue(noConnectionError)
                        }
                        else -> {
                            eventCheckInLiveData.postValue(null)
                            screenLiveData.postValue(unknownError)
                        }
                    }
                }
            }
        }
    }

    fun inputsIsValid(name: String, email: String): Boolean {
        var inputsIsValid = true
        if(name.isEmpty()) {
            inputNameErrorMessageLiveData.value = insertYourName
            inputsIsValid = false
        } else if(name.nameIsInvalid()) {
            inputNameErrorMessageLiveData.value = nameIsInvalid
            inputsIsValid = false
        }
        if(email.isEmpty()) {
            inputEmailErrorMessageLiveData.value = insertYourEmail
            inputsIsValid = false
        } else if(email.emailIsInvalid()) {
            inputEmailErrorMessageLiveData.value = emailIsInvalid
            inputsIsValid = false
        }
        return inputsIsValid
    }

    override fun onCleared() {
        super.onCleared()
        unloadKoinModules(eventCheckInModule)
    }
}