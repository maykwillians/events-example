package com.maykmenezes.desafiosicred.features.eventsList.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.maykmenezes.desafiosicred.R
import com.maykmenezes.desafiosicred.features.eventDatails.view.EventDetailsFragment
import com.maykmenezes.desafiosicred.features.eventsList.di.EventsListModule.eventsListModule
import com.maykmenezes.desafiosicred.features.eventsList.model.response.EventItemResponse
import com.maykmenezes.desafiosicred.features.eventsList.view.adapter.EventsListAdapter
import com.maykmenezes.desafiosicred.util.ui.ScreenStatus
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.fragment_events_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

private const val TAG = "fragment_events_list_tag_id"

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
            eventsListLiveData.observe(viewLifecycleOwner, {
                events_list.adapter = EventsListAdapter(it) { event ->
                    gotoEventDetailsScreen(event)
                }
            })
            screenLiveData.observe(viewLifecycleOwner, {
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

    private fun updateScreen(screenStatus: ScreenStatus) {
        screenStatus.run {
            loading.visibility = loadingVisibility
            content.visibility = contentVisibility
            error.visibility = errorVisibility ?: GONE
            errorImage.setImageDrawable(ContextCompat.getDrawable(requireActivity(), errorIcon ?: R.drawable.ic_error))
            errorDescription.text = resources.getString(errorMessage ?: R.string.errorRequest)
        }
    }
}