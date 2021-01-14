package com.maykmenezes.desafiosicred.eventsList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.maykmenezes.desafiosicred.eventsList.EventsListViewModelTestHelper.fakeEvent
import com.maykmenezes.desafiosicred.eventsList.EventsListViewModelTestHelper.errorResponse
import com.maykmenezes.desafiosicred.eventsList.EventsListViewModelTestHelper.successResponse
import com.maykmenezes.desafiosicred.features.eventsList.model.response.EventItemResponse
import com.maykmenezes.desafiosicred.features.eventsList.repository.EventsListRepository
import com.maykmenezes.desafiosicred.features.eventsList.view.EventsListViewModel
import com.maykmenezes.desafiosicred.util.ui.FakeScreenBehavior.FakeCommonScreenBehavior.loading
import com.maykmenezes.desafiosicred.util.ui.FakeScreenBehavior.FakeCommonScreenBehavior.success
import com.maykmenezes.desafiosicred.util.ui.FakeScreenBehavior.FakeCommonScreenBehavior.error
import io.mockk.*
import org.junit.*

class EventsListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<EventsListRepository>()
    private val eventsListObserver = mockk<Observer<List<EventItemResponse>>>(relaxed = true)
    private val screenObserver = mockk<Observer<Triple<Int, Int, Int>>>(relaxed = true)

    private lateinit var viewModel: EventsListViewModel

    @Before
    fun setup() {
        viewModel = EventsListViewModel(repository = repository)
    }

    @Test
    fun `when to request events then should call repository and put response value on live data`() {
        viewModel.eventsListLiveData.observeForever(eventsListObserver)

        coEvery { repository.fetchEvents() } returns successResponse

        viewModel.fetchEvents()

        coVerify { repository.fetchEvents() }
        verify { eventsListObserver.onChanged(listOf(fakeEvent)) }
        Assert.assertEquals(viewModel.eventsListLiveData.value, listOf(fakeEvent))
    }

    @Test
    fun `when to request events and to return success response then should set loading screen state then success`() {
        viewModel.screenLiveData.observeForever(screenObserver)

        coEvery { repository.fetchEvents() } returns successResponse

        viewModel.fetchEvents()

        verifySequence {
            screenObserver.onChanged(loading)
            screenObserver.onChanged(success)
        }
    }

    @Test
    fun `when to request events and to return error response then should set loading screen state then error`() {
        viewModel.screenLiveData.observeForever(screenObserver)

        coEvery { repository.fetchEvents() } returns errorResponse

        viewModel.fetchEvents()

        verifySequence {
            screenObserver.onChanged(loading)
            screenObserver.onChanged(error)
        }
    }
}