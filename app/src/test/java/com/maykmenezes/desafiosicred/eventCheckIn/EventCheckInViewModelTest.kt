package com.maykmenezes.desafiosicred.eventCheckIn

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.maykmenezes.desafiosicred.eventCheckIn.EventCheckInViewModelTestHelper.checkInRequest
import com.maykmenezes.desafiosicred.eventCheckIn.EventCheckInViewModelTestHelper.fakeSuccessCheckIn
import com.maykmenezes.desafiosicred.eventCheckIn.EventCheckInViewModelTestHelper.successResponse
import com.maykmenezes.desafiosicred.eventCheckIn.EventCheckInViewModelTestHelper.unknownErrorResponse
import com.maykmenezes.desafiosicred.features.eventCheckIn.repository.EventCheckInRepository
import com.maykmenezes.desafiosicred.features.eventCheckIn.view.EventCheckInViewModel
import com.maykmenezes.desafiosicred.util.ui.CustomScreenStates
import com.maykmenezes.desafiosicred.util.ui.ScreenStatus
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EventCheckInViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<EventCheckInRepository>()
    private val eventCheckInObserver = mockk<Observer<String>>(relaxed = true)
    private val screenObserver = mockk<Observer<ScreenStatus>>(relaxed = true)

    private lateinit var viewModel: EventCheckInViewModel

    @Before
    fun setup() {
        viewModel = EventCheckInViewModel(
                repository = repository,
                coroutineDispatcher = Dispatchers.Unconfined)
    }

    @Test
    fun `not should to allow empty or invalid inputs`() {
        val validName = "mayk de"
        val validEmail = "mayk@test.com"
        val invalidName = "23mayk"
        val invalidEmail = "mayktest.com"
        val emptyName = ""
        val emptyEmail = ""
        val emptyNameWithSpace = " "
        val emptyEmailWithSpace = " "

        assertTrue(viewModel.inputsIsValid(validName, validEmail))
        assertFalse(viewModel.inputsIsValid(invalidName, invalidEmail))
        assertFalse(viewModel.inputsIsValid(emptyName, emptyEmail))
        assertFalse(viewModel.inputsIsValid(emptyNameWithSpace, emptyEmailWithSpace))
    }

    @Test
    fun `when to post a valid event checkIn then should call repository and put response value on live data`() {
        viewModel.eventCheckInLiveData.observeForever(eventCheckInObserver)
        coEvery { repository.checkIn(checkInRequest) } returns successResponse

        checkInRequest.run {
            viewModel.checkIn(
                    name = name,
                    email = email,
                    eventId = eventId)
        }

        coVerify { repository.checkIn(checkInRequest) }
        verify { eventCheckInObserver.onChanged(fakeSuccessCheckIn) }
        assertEquals(viewModel.eventCheckInLiveData.value, fakeSuccessCheckIn)
    }

    @Test
    fun `when to return success response then should set loading screen state then success`() {
        viewModel.screenLiveData.observeForever(screenObserver)
        coEvery { repository.checkIn(checkInRequest) } returns successResponse

        checkInRequest.run {
            viewModel.checkIn(
                    name = name,
                    email = email,
                    eventId = eventId)
        }

        verifySequence {
            screenObserver.onChanged(CustomScreenStates.loading)
            screenObserver.onChanged(CustomScreenStates.success)
        }
    }

    @Test
    fun `when to return unknown error response then should set loading screen state then unknown error`() {
        viewModel.screenLiveData.observeForever(screenObserver)
        coEvery { repository.checkIn(checkInRequest) } returns unknownErrorResponse

        checkInRequest.run {
            viewModel.checkIn(
                    name = name,
                    email = email,
                    eventId = eventId)
        }

        verifySequence {
            screenObserver.onChanged(CustomScreenStates.loading)
            screenObserver.onChanged(CustomScreenStates.unknownError)
        }
    }
}