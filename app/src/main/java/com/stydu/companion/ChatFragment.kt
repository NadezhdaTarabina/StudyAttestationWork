package com.stydu.companion

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARRAY_USER = "ARRAY_USER"
private const val ARRAY_MESSAGE = "ARRAY_MESSAGE"
private const val ARRAY_SIZE = "ARRAY_SIZE"


class CompanionChatFragment : Fragment() {


    private lateinit var companionImageView: ImageView
    private lateinit var sentButton: Button
    private lateinit var messageEditText: EditText
    private lateinit var companionAnim: AnimatedVectorDrawable
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var adapterMessageRecyclerView: MessageListAdapter
    private var messageList: ArrayList<Message> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            val arrayMessageAdapterUser =
                savedInstanceState.getStringArrayList(ARRAY_USER) as ArrayList<String>

            val arrayMessageAdapterMessageText =
                savedInstanceState.getStringArrayList(ARRAY_MESSAGE) as ArrayList<String>

            for (i in 0 until savedInstanceState.getInt(ARRAY_SIZE)) {
                val message = Message(arrayMessageAdapterUser[i], arrayMessageAdapterMessageText[i])
                messageList.add(message)
            }
        }
    }


    @SuppressLint("WrongConstant", "NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_companion_chat, container, false)

        //changing the orientation of the LinearLayout from the screen position
        val messageLayout: LinearLayout = view.findViewById(R.id.message_and_companion_image_Layout)
        if (getScreenOrientation())
            messageLayout.orientation = LinearLayout.VERTICAL
        else
            messageLayout.orientation = LinearLayout.HORIZONTAL


        //Initializing ImageView and setting background
        companionImageView = view.findViewById(R.id.companion_talk_image_view)
        companionImageView.setBackgroundResource(R.drawable.duck_anim)
        companionAnim = companionImageView.background as AnimatedVectorDrawable

        //Initializing RecyclerView
        messageRecyclerView = view.findViewById(R.id.message_list_recycler_view)
        val layoutManagerMessageRecyclerView = LinearLayoutManager(view.context)
        messageRecyclerView.layoutManager = layoutManagerMessageRecyclerView
        adapterMessageRecyclerView = MessageListAdapter(messageList)
        messageRecyclerView.adapter = adapterMessageRecyclerView


        //Initialization of message sending elements
        sentButton = view.findViewById(R.id.send_button)
        messageEditText = view.findViewById(R.id.message_edit_text)

        //Sending messages by the user
        sentButton.setOnClickListener {
            if (messageEditText.text.isNotEmpty()) {

                val messageMy = Message(MY_NAME, messageEditText.text.toString())

                messageEditText.text.clear()
                messageList.add(messageMy)
                adapterMessageRecyclerView.notifyDataSetChanged()

                val inputManager =
                    activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                    view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )


                Thread.sleep(500)
                companionAnim.start()
                val messageCompanion = Message("Duck", getMessageCompanion(messageMy.message))
                messageList.add(messageCompanion)
                adapterMessageRecyclerView.notifyDataSetChanged()
                layoutManagerMessageRecyclerView.scrollToPosition(adapterMessageRecyclerView.itemCount - 1)
            } else {
                Toast.makeText(activity, "Message should not be empty", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }


    override fun onSaveInstanceState(outState: Bundle) {
        //Saving data
        val arrayMessageAdapterUser: ArrayList<String> = ArrayList()
        val arrayMessageAdapterMessageText: ArrayList<String> = ArrayList()
        for (i in 0 until messageList.size) {
            arrayMessageAdapterUser.add(messageList[i].user)
            arrayMessageAdapterMessageText.add(messageList[i].message)
        }
        outState.putStringArrayList(ARRAY_USER, arrayMessageAdapterUser)
        outState.putStringArrayList(ARRAY_MESSAGE, arrayMessageAdapterMessageText)
        outState.putInt(ARRAY_SIZE, arrayMessageAdapterUser.size)

        super.onSaveInstanceState(outState)

    }


    private fun getScreenOrientation(): Boolean {
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> true
            Configuration.ORIENTATION_LANDSCAPE -> false
            else -> true
        }
    }

    private fun getMessageCompanion(message: String): String {
        return when (message.length) {
            in 1..5 -> "Quack!"
            in 5..30 -> "Quack, quack"
            else -> "Quack, quack, quack"
        }
    }


}




