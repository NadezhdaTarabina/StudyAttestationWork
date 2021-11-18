package com.stydu.companion

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var massageText: TextView = itemView.findViewById(R.id.message_text_view)

    fun bind(message: Message) {
        massageText.text = message.message
    }
}