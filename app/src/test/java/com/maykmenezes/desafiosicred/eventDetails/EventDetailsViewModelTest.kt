package com.maykmenezes.desafiosicred.eventDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.maykmenezes.desafiosicred.eventDetails.EventDetailsViewModelTestHelper.fakeEvent
import com.maykmenezes.desafiosicred.eventDetails.EventDetailsViewModelTestHelper.successResponse
import com.maykmenezes.desafiosicred.eventDetails.EventDetailsViewModelTestHelper.unknownErrorResponse
import com.maykmenezes.desafiosicred.features.eventDatails.model.response.EventDetailsResponse
import com.maykmenezes.desafiosicred.features.eventDatails.repository.EventDetailsRepository
import com.maykmenezes.desafiosicred.features.eventDatails.view.EventDetailsViewModel
import com.maykmenezes.desafiosicred.util.ui.CustomScreenStates
import com.maykmenezes.desafiosicred.util.ui.ScreenStatus
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EventDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<EventDetailsRepository>()
    private val eventObserver = mockk<Observer<EventDetailsResponse>>(relaxed = true)
    private val screenObserver = mockk<Observer<ScreenStatus>>(relaxed = true)

    private lateinit var viewModel: EventDetailsViewModel

    @Before
    fun setup() {
        viewModel = EventDetailsViewModel(
            repository = repository,
            coroutineDispatcher = Dispatchers.Unconfined)
    }

    @Test
    fun `when to request event details then should call repository and put response value on live data`() {
        viewModel.eventDetailsLiveData.observeForever(eventObserver)
        val eventId = "1"
        coEvery { repository.fetchEvent(eventId) } returns successResponse

        viewModel.fetchEvent(eventId)

        coVerify { repository.fetchEvent(eventId) }
        verify { eventObserver.onChanged(fakeEvent) }
        assertEquals(viewModel.eventDetailsLiveData.value, fakeEvent)
    }

    @Test
    fun `when to return success response then should set loading screen state then success`() {
        viewModel.screenLiveData.observeForever(screenObserver)
        val eventId = "1"
        coEvery { repository.fetchEvent(eventId) } returns successResponse

        viewModel.fetchEvent(eventId)

        verifySequence {
            screenObserver.onChanged(CustomScreenStates.loading)
            screenObserver.onChanged(CustomScreenStates.success)
        }
    }

    @Test
    fun `when to return unknown error response then should set loading screen state then unknown error`() {
        viewModel.screenLiveData.observeForever(screenObserver)
        val eventId = "1"
        coEvery { repository.fetchEvent(eventId) } returns unknownErrorResponse

        viewModel.fetchEvent(eventId)

        verifySequence {
            screenObserver.onChanged(CustomScreenStates.loading)
            screenObserver.onChanged(CustomScreenStates.unknownError)
        }
    }
}