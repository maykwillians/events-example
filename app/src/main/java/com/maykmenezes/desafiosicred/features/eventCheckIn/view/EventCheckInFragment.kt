package com.maykmenezes.desafiosicred.features.eventCheckIn.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.maykmenezes.desafiosicred.R
import com.maykmenezes.desafiosicred.features.eventCheckIn.di.EventCheckInModule.eventCheckInModule
import kotlinx.android.synthetic.main.fragment_event_check_in.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

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
            checkInDialogResultLiveData.observe(requireActivity(), {
                showResultCheckInDialog(it.first, it.second, it.third)
            })
            inputNameErrorMessageLiveData.observe(requireActivity(), {
                inputName.error = it
            })
            inputEmailErrorMessageLiveData.observe(requireActivity(), {
                inputEmail.error = it
            })
            screenLiveData.observe(requireActivity(), {
                updateScreen(it)
            })
        }
    }

    private fun hideKeyboard() {
        val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun showResultCheckInDialog(title: Int, message: Int, textButton: Int) {
        AlertDialog.Builder(requireActivity())
                .setTitle(resources.getString(title))
                .setMessage(resources.getString(message))
                .setCancelable(false)
                .setPositiveButton(resources.getString(textButton)) { _, _ -> }.show()
    }

    private fun updateScreen(screenBehavior: Pair<Int, Int>) {
        hideKeyboard()
        loading.visibility = screenBehavior.first
        content.visibility = screenBehavior.second
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

    override fun onDetach() {
        super.onDetach()
        unloadKoinModules(eventCheckInModule)
    }
}