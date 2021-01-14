package com.maykmenezes.desafiosicred.features.eventCheckIn.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maykmenezes.desafiosicred.features.eventCheckIn.model.request.CheckInRequest
import com.maykmenezes.desafiosicred.features.eventCheckIn.repository.EventCheckInRepository
import com.maykmenezes.desafiosicred.features.eventCheckIn.view.DialogCheckInResultBehavior.success
import com.maykmenezes.desafiosicred.features.eventCheckIn.view.DialogCheckInResultBehavior.error
import com.maykmenezes.desafiosicred.util.ui.ScreenBehavior.CommonScreenBehaviorWithoutErrorLayout.loading
import com.maykmenezes.desafiosicred.util.ui.ScreenBehavior.CommonScreenBehaviorWithoutErrorLayout.terminated
import com.maykmenezes.desafiosicred.util.data.emailIsInvalid
import com.maykmenezes.desafiosicred.util.data.nameIsInvalid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class EventCheckInViewModel(private val repository: EventCheckInRepository) : ViewModel() {

    val checkInDialogResultLiveData = MutableLiveData<Triple<Int, Int, Int>>()
    val screenLiveData = MutableLiveData<Pair<Int, Int>>()
    val inputNameErrorMessageLiveData = MutableLiveData<String>()
    val inputEmailErrorMessageLiveData = MutableLiveData<String>()

    fun checkIn(eventId: String, name: String, email: String) {
        if(inputsIsValid(name, email)) {
            screenLiveData.postValue(loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repository.checkIn(CheckInRequest(eventId, name, email))
                    if(response.isSuccessful) {
                        checkInDialogResultLiveData.postValue(success)
                        screenLiveData.postValue(terminated)
                    } else {
                        checkInDialogResultLiveData.postValue(error)
                        screenLiveData.postValue(terminated)
                    }
                } catch(e: Exception) {
                    checkInDialogResultLiveData.postValue(error)
                    screenLiveData.postValue(terminated)
                }
            }
        }
    }

    private fun inputsIsValid(name: String, email: String): Boolean {
        var inputsInValid = true
        if(name.isEmpty()) {
            inputNameErrorMessageLiveData.value = "Insira o nome"
            inputsInValid = false
        } else if(name.nameIsInvalid()) {
            inputNameErrorMessageLiveData.value = "Nome inválido"
            inputsInValid = false
        }
        if(email.isEmpty()) {
            inputEmailErrorMessageLiveData.value = "Insira o email"
            inputsInValid = false
        } else if(email.emailIsInvalid()) {
            inputEmailErrorMessageLiveData.value = "Email inválido"
            inputsInValid = false
        }
        return inputsInValid
    }
}