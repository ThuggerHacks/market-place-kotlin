package com.example.marketplace.View.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.marketplace.Adapter.ChatListAdapter
import com.example.marketplace.Adapter.Listeners.OnChatClickListener
import com.example.marketplace.Model.Chat
import com.example.marketplace.R
import com.example.marketplace.Repository.ChatRepository
import com.example.marketplace.View.MessageActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MessageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MessageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var chatAdapter: ChatListAdapter
    private lateinit var chatList:ArrayList<Chat>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_message, container, false)
        val storage = view.context.getSharedPreferences("user", Context.MODE_PRIVATE)
        val userId = storage.getInt("id",0)
        loadChats(view, userId)
        return view
    }


    fun loadChats(view:View, id:Int){

        val messageViewer = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.message_fragment)

        val chatRepository = ChatRepository()
        messageViewer.layoutManager = LinearLayoutManager(view.context)
        chatRepository.getChatsByUserId(id){ list ->
            chatAdapter = ChatListAdapter(list, OnChatClickListener {
                val intent = Intent(requireContext(),MessageActivity::class.java)
                intent.putExtra("chatId",it.id)
                requireContext().startActivity(intent)
            })

            messageViewer.adapter = chatAdapter
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MessageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MessageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}