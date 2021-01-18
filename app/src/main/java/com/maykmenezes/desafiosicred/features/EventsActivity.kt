package com.maykmenezes.desafiosicred.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maykmenezes.desafiosicred.R
import com.maykmenezes.desafiosicred.features.eventsList.view.EventsListFragment

class EventsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        if(savedInstanceState == null) {
            gotoEventsListScreen()
        }
    }

    private fun gotoEventsListScreen() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content, EventsListFragment())
        fragmentTransaction.commit()
    }
}