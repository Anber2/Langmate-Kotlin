package com.langmate.langmate.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.langmate.langmate.Models.ChatsModel
import com.langmate.langmate.R
import java.util.*

/**
 * Created by HP on 3/7/2018.
 */

class ChatsAdapter(internal var chatsModelArrayList: ArrayList<ChatsModel>, internal var context: Context) : BaseAdapter() {
    internal var chatsModel: ChatsModel? = null

    init {

        inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return chatsModelArrayList.size
    }

    override fun getItem(i: Int): ChatsModel {
        return chatsModelArrayList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val vh: ViewHolder


        if (view == null) {
            view = inflater!!.inflate(R.layout.z_chat_list_item, null)

            vh = ViewHolder()
            vh.chatName = view!!.findViewById(R.id.chatName)
            vh.chatMsg = view.findViewById(R.id.chatMsg)
            vh.chatTime = view.findViewById(R.id.chatTime)


            view.tag = vh

        } else {
            vh = view.tag as ViewHolder
        }

        vh.chatName!!.text = chatsModelArrayList[i].nameChat
        vh.chatMsg!!.text = chatsModelArrayList[i].msgChat
        vh.chatTime!!.text = chatsModelArrayList[i].timeChat


        return view
    }

    class ViewHolder {

        internal var button_calender_listOfGames_delete: Button? = null
        internal var imageView2: ImageView? = null
        internal var linearLayout_gameListItem: LinearLayout? = null
        internal var chatName: TextView? = null
        internal var chatMsg: TextView? = null
        internal var chatTime: TextView? = null
        internal var textView_daysToGo: TextView? = null
    }

    companion object {
        private var inflater: LayoutInflater? = null
    }
}
