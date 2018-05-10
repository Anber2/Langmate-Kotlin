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
class ChatsFragment : MainBaseFragment() {

    internal lateinit var v: View

    internal lateinit var chatsListView: ListView

    internal lateinit var chatsAdapter: ChatsAdapter

    internal lateinit var chatsModelArrayList: ArrayList<ChatsModel>
    internal var chatsModel: ChatsModel? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater!!.inflate(R.layout.chats_fragment, container, false)

      chatsListView = v.findViewById(R.id.list_of_chat_rooms)







        return v
    }





}