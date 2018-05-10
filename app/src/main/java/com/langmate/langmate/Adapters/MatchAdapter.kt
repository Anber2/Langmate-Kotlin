package com.langmate.langmate.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.langmate.langmate.Activities.MainActivity
import com.langmate.langmate.Models.MatcheModel
import com.langmate.langmate.R
import kotlinx.android.synthetic.main.item_match.view.*

/**
 * Created by HP on 3/1/2018.
 */
class MatchAdapter(val context: Context, val data: List<MatcheModel>?) : BaseAdapter() {

    private val dataList = mutableListOf<MatcheModel>()


    init {
        if (data != null) {
            dataList.addAll(data)
        }
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): MatcheModel {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<MatcheModel>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val holder: DataViewHolder
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
            holder = DataViewHolder(view)
            view?.tag = holder
        } else {
            holder = view.tag as DataViewHolder
        }

        holder.bindData(context, getItem(position))

        try {
            holder.dislike.setOnClickListener {

                MainActivity.koloda.onClickLeft()

                MainActivity.UpdateDisLikeUser(getItem(position).docId);



            }
            holder.like.setOnClickListener {
                MainActivity.koloda.onClickRight()

                MainActivity.UpdateLikeUser(getItem(position).docId!!);
            }

        } catch (e: Exception) {
        }

        return view
    }

    /**
     * Static view items holder
     */
    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var picture = view.kolodaImage
        var matchName = view.textView_matchName
        var matchAge = view.textView_matchAge
        var matchDistance = view.textView_matchDistance
        var matchCity = view.text_matchCity
        var dislike = view.dislike_
        var like = view.like_


        internal fun bindData(context: Context, data: MatcheModel) {
            val transforms = RequestOptions().transforms(CenterCrop(), RoundedCorners(20))
            Glide.with(context)
                    .load(data.matchImg)
                    .apply(transforms)
                    .into(picture)

            matchName.setText(data.matchName)
            matchAge.setText(data.matchAge)
            matchDistance.setText(data.matchDistance)
            matchCity.setText(data.matchLocationName)


           /* if (data.isLike.equals("true")) {

                like.setBackgroundColor(Color.RED)
            }*/


        }

    }
}

