package com.maykmenezes.desafiosicred.features.eventsList.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maykmenezes.desafiosicred.R
import com.maykmenezes.desafiosicred.features.eventDatails.view.EventDetailsFragment
import com.maykmenezes.desafiosicred.features.eventsList.di.EventsListModule.eventsListModule
import com.maykmenezes.desafiosicred.features.eventsList.model.response.EventItemResponse
import com.maykmenezes.desafiosicred.features.eventsList.view.adapter.EventsListAdapter
import kotlinx.android.synthetic.main.fragment_events_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

private const val TAG = "fragment_events_list_tag"

class EventsListFragment : Fragment() {

    private val viewModel: EventsListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(eventsListModule)
        viewModel.fetchEvents()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_events_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
        observeDataChanges()
    }

    private fun observeDataChanges() {
        viewModel.run {
            eventsListLiveData.observe(requireActivity(), {
                events_list.adapter = EventsListAdapter(it) { event ->
                    gotoEventDetailsScreen(event)
                }
            })
            screenLiveData.observe(requireActivity(), {
                updateScreen(it)
            })
        }
    }

    private fun gotoEventDetailsScreen(event: EventItemResponse) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content, EventDetailsFragment.newInstance(event.id ?: ""))
        fragmentTransaction.addToBackStack(TAG)
        fragmentTransaction.commit()
    }

    private fun setListeners() {
        buttonRetry.setOnClickListener {
            viewModel.fetchEvents()
        }
    }

    private fun updateScreen(screenBehavior: Triple<Int, Int, Int>) {
        loading.visibility = screenBehavior.first
        content.visibility = screenBehavior.second
        error.visibility = screenBehavior.third
    }

    override fun onDetach() {
        super.onDetach()
        unloadKoinModules(eventsListModule)
    }
}