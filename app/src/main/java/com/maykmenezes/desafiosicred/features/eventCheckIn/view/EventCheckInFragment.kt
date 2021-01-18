package com.maykmenezes.desafiosicred.features.eventCheckIn.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.maykmenezes.desafiosicred.R
import com.maykmenezes.desafiosicred.features.eventCheckIn.di.EventCheckInModule.eventCheckInModule
import com.maykmenezes.desafiosicred.util.ui.ScreenStatus
import kotlinx.android.synthetic.main.fragment_event_check_in.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

private const val EVENT_ID = "event_id"

class EventCheckInFragment : Fragment() {

    private var eventId: String? = null
    private val viewModel: EventCheckInViewModel by viewModel()

    companion object {
        fun newInstance(eventId: String) =
            EventCheckInFragment().apply {
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
        loadKoinModules(eventCheckInModule)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_event_check_in, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
        observeDataChanges()
    }

    private fun observeDataChanges() {
        viewModel.run {
            eventCheckInLiveData.observe(viewLifecycleOwner, {
                showResultCheckInDialog(it)
            })
            inputNameErrorMessageLiveData.observe(viewLifecycleOwner, {
                inputName.error = resources.getString(it)
            })
            inputEmailErrorMessageLiveData.observe(viewLifecycleOwner, {
                inputEmail.error = resources.getString(it)
            })
            screenLiveData.observe(viewLifecycleOwner, {
                updateScreen(it)
            })
        }
    }

    private fun hideKeyboard() {
        val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun showResultCheckInDialog(message: String?) {
        AlertDialog.Builder(requireActivity())
            .setTitle(
                if(message == null) {
                    resources.getString(R.string.textError)
                } else {
                    resources.getString(R.string.textSuccess)
                }
            )
            .setMessage(message ?: resources.getString(R.string.textCheckInError))
            .setCancelable(false)
            .setPositiveButton(resources.getString(R.string.textButtonOk)) { _, _ ->
                content.visibility = VISIBLE
            }.show()
    }

    private fun updateScreen(screenStatus: ScreenStatus) {
        hideKeyboard()
        screenStatus.run {
            loading.visibility = loadingVisibility
            content.visibility = contentVisibility
        }
    }

    private fun setListeners() {
        buttonCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }
        buttonCheckIn.setOnClickListener {
            val name = inputName.text.toString()
            val email = inputEmail.text.toString()
            viewModel.checkIn(eventId ?: "", name, email)
        }
    }
}