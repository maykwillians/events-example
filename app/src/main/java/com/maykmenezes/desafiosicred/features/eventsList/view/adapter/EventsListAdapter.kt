package com.maykmenezes.desafiosicred.features.eventsList.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maykmenezes.desafiosicred.R
import com.maykmenezes.desafiosicred.features.eventsList.model.response.EventItemResponse

class EventsListAdapter(
    private val events: List<EventItemResponse>,
    private val callback: ((event: EventItemResponse) -> Unit)) : RecyclerView.Adapter<EventsListAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsListAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item_layout, parent, false)
        return EventsListAdapterViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: EventsListAdapterViewHolder, position: Int) {
        holder.bindView(events[position])
    }

    override fun getItemCount() = events.count()
}