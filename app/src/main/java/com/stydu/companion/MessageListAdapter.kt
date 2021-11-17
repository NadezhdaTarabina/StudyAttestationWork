package com.stydu.companion

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

private const val VIEW_TYPE_MESSAGE_MY = 0
private const val VIEW_TYPE_MESSAGE_COMPANION = 1


class MessageListAdapter(private val context: Context): RecyclerView.Adapter<MessageListViewHolder>() {

    private val messageList: ArrayList<Message> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageListViewHolder {
    return if (viewType == VIEW_TYPE_MESSAGE_MY){
        MessageListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.message_my, parent, false)
        )
    }
        else {
        MessageListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.message_companion, parent, false)
        )
    }
    }

    override fun onBindViewHolder(holder: MessageListViewHolder, position: Int) {
        val message = messageList[position]

        holder?.bind(message)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messageList[position]

        return if (MY_NAME == message.user){
            VIEW_TYPE_MESSAGE_MY
        }
        else {
            VIEW_TYPE_MESSAGE_COMPANION
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addMessage(message: Message){
        messageList.add(message)
        notifyDataSetChanged()
    }



}


