package com.langmate.langmate.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.langmate.langmate.Adapters.ChatsAdapter
import com.langmate.langmate.Models.ChatsModel
import com.langmate.langmate.R
import java.util.*

/**
 * Created by HP on 3/1/2018.
 */
class ChatsFragment  : MainBaseFragment() {

    internal lateinit var v: View

    internal lateinit var chatsListView: ListView

    internal lateinit var chatsAdapter: ChatsAdapter

    internal lateinit var chatsModelArrayList: ArrayList<ChatsModel>
    internal var chatsModel: ChatsModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater!!.inflate(R.layout.chats_fragment, container, false)

        chatsListView = v.findViewById(R.id.chatsListView)


        getChatItems()



        return v
    }

    private fun getChatItems() {
        chatsModelArrayList = ArrayList()

        chatsModelArrayList.add(ChatsModel("0", "", "Name 1", "Hi ....", "10 AM" ))
        chatsModelArrayList.add(ChatsModel("1", "", "Name 2", "Hello ...", "12 PM" ))
        chatsModelArrayList.add(ChatsModel("2", "", "Name 3", "message", "2 PM" ))



        chatsAdapter = ChatsAdapter( chatsModelArrayList, context)

        chatsListView.adapter = chatsAdapter
     }
}