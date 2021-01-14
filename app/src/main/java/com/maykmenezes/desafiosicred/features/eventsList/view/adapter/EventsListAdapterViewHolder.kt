package com.maykmenezes.desafiosicred.features.eventsList.view.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maykmenezes.desafiosicred.R
import com.maykmenezes.desafiosicred.features.eventsList.model.response.EventItemResponse
import com.maykmenezes.desafiosicred.util.data.timeStampToFormattedDataTime
import kotlinx.android.synthetic.main.event_item_layout.view.*

class EventsListAdapterViewHolder(
    itemView: View,
    private val callback: ((event: EventItemResponse) -> Unit)) : RecyclerView.ViewHolder(itemView) {

    fun bindView(event: EventItemResponse) {

        val eventTitle = itemView.findViewById<TextView>(R.id.event_title)
        eventTitle.text = event.title

        val eventDate = itemView.findViewById<TextView>(R.id.event_date)
        eventDate.text = event.date?.timeStampToFormattedDataTime()

        setImage(event.image ?: "")

        itemView.setOnClickListener {
            callback(event)
        }
    }

    private fun setImage(imageUrl: String) {
        Glide.with(itemView)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_event_image_fallback)
            .into(itemView.event_image)
    }
}