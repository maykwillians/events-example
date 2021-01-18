package com.maykmenezes.desafiosicred.features.eventDatails.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.maykmenezes.desafiosicred.R
import com.maykmenezes.desafiosicred.features.eventCheckIn.view.EventCheckInFragment
import com.maykmenezes.desafiosicred.features.eventDatails.di.EventDetailsModule.eventDetailsModule
import com.maykmenezes.desafiosicred.features.eventDatails.model.response.EventDetailsResponse
import com.maykmenezes.desafiosicred.util.data.doubleToFormattedCurrentMoney
import com.maykmenezes.desafiosicred.util.data.timeStampToFormattedDataTime
import com.maykmenezes.desafiosicred.util.ui.ScreenStatus
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.fragment_event_details.*
import kotlinx.android.synthetic.main.fragment_event_details.content
import kotlinx.android.synthetic.main.fragment_event_details.error
import kotlinx.android.synthetic.main.fragment_event_details.loading
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

private const val EVENT_ID = "event_id"
private const val TAG = "fragment_event_details_tag_id"
private const val GOOGLE_MAPS_PACKAGE = "com.google.android.apps.maps"

class EventDetailsFragment : Fragment() {

    private var eventId: String? = null
    private val viewModel: EventDetailsViewModel by viewModel()

    companion object {
        fun newInstance(eventId: String) = EventDetailsFragment().apply {
            arguments = Bundle().apply {
                putString(EVENT_ID, eventId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            eventId = it.getString(EVENT_ID)
        }
        loadKoinModules(eventDetailsModule)
        viewModel.fetchEvent(eventId ?: "")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_event_details, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
        observeDataChanges()
    }

    private fun observeDataChanges() {
        viewModel.run {
            eventDetailsLiveData.observe(viewLifecycleOwner, {
                setupEvent(it)
            })
            screenLiveData.observe(viewLifecycleOwner, {
                updateScreen(it)
            })
        }
    }

    private fun gotoEventCheckInScreen(eventId: String) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content, EventCheckInFragment.newInstance(eventId))
        fragmentTransaction.addToBackStack(TAG)
        fragmentTransaction.commit()
    }

    private fun setListeners() {
        buttonRetry.setOnClickListener {
            viewModel.fetchEvent(eventId ?: "")
        }
        buttonCheckIn.setOnClickListener {
            gotoEventCheckInScreen(eventId ?: "")
        }
        buttonShare.setOnClickListener {
            shareEvent(viewModel.eventDetailsLiveData.value)
        }
        eventPosition.setOnClickListener {
            showEventPositionOnMap(
                viewModel.eventDetailsLiveData.value?.latitude,
                viewModel.eventDetailsLiveData.value?.longitude)
        }
    }

    private fun setupEvent(event: EventDetailsResponse) {
        eventTitle.text = event.title
        eventDescription.text = event.description
        eventDate.text = event.date?.timeStampToFormattedDataTime()
        eventPrice.text = event.price?.doubleToFormattedCurrentMoney()
        setImage(event.image ?: "")
    }

    private fun showEventPositionOnMap(lat: Double?, lang: Double?) {
        val gmmIntentUri = Uri.parse("geo:$lat,$lang")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage(GOOGLE_MAPS_PACKAGE)
        startActivity(mapIntent)
    }

    private fun setImage(imageUrl: String) {
        Glide.with(requireActivity())
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_event_image_fallback)
            .into(eventImage)
    }

    private fun shareEvent(event: EventDetailsResponse?) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, event?.description)
            type = "text/plain"
        }
        event?.let {
            startActivity(Intent.createChooser(sendIntent, it.title))
        }
    }

    private fun updateScreen(screenStatus: ScreenStatus) {
        screenStatus.run {
            loading.visibility = loadingVisibility
            content.visibility = contentVisibility
            error.visibility = errorVisibility ?: View.GONE
            errorImage.setImageDrawable(ContextCompat.getDrawable(requireActivity(), errorIcon ?: R.drawable.ic_error))
            errorDescription.text = resources.getString(errorMessage ?: R.string.errorRequest)
        }
    }
}