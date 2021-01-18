package com.maykmenezes.desafiosicred.eventsList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.maykmenezes.desafiosicred.eventsList.EventsListViewModelTestHelper.fakeEvent
import com.maykmenezes.desafiosicred.eventsList.EventsListViewModelTestHelper.unknownErrorResponse
import com.maykmenezes.desafiosicred.eventsList.EventsListViewModelTestHelper.successResponse
import com.maykmenezes.desafiosicred.features.eventsList.model.response.EventItemResponse
import com.maykmenezes.desafiosicred.features.eventsList.repository.EventsListRepository
import com.maykmenezes.desafiosicred.features.eventsList.view.EventsListViewModel
import com.maykmenezes.desafiosicred.util.ui.CustomScreenStates.loading
import com.maykmenezes.desafiosicred.util.ui.CustomScreenStates.success
import com.maykmenezes.desafiosicred.util.ui.CustomScreenStates.unknownError
import com.maykmenezes.desafiosicred.util.ui.ScreenStatus
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import org.junit.*
import org.junit.Assert.assertEquals

class EventsListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<EventsListRepository>()
    private val eventsListObserver = mockk<Observer<List<EventItemResponse>>>(relaxed = true)
    private val screenObserver = mockk<Observer<ScreenStatus>>(relaxed = true)

    private lateinit var viewModel: EventsListViewModel

    @Before
    fun setup() {
        viewModel = EventsListViewModel(
                repository = repository,
                coroutineDispatcher = Dispatchers.Unconfined)
    }

    @Test
    fun `when to request events list then should call repository and put response value on live data`() {
        viewModel.eventsListLiveData.observeForever(eventsListObserver)
        coEvery { repository.fetchEvents() } returns successResponse

        viewModel.fetchEvents()

        coVerify { repository.fetchEvents() }
        verify { eventsListObserver.onChanged(listOf(fakeEvent)) }
        assertEquals(viewModel.eventsListLiveData.value, listOf(fakeEvent))
    }

    @Test
    fun `when to return success response then should set loading screen state then success`() {
        viewModel.screenLiveData.observeForever(screenObserver)
        coEvery { repository.fetchEvents() } returns successResponse

        viewModel.fetchEvents()

        verifySequence {
            screenObserver.onChanged(loading)
            screenObserver.onChanged(success)
        }
    }

    @Test
    fun `when to return unknown error response then should set loading screen state then unknown error`() {
        viewModel.screenLiveData.observeForever(screenObserver)
        coEvery { repository.fetchEvents() } returns unknownErrorResponse

        viewModel.fetchEvents()

        verifySequence {
            screenObserver.onChanged(loading)
            screenObserver.onChanged(unknownError)
        }
    }
}