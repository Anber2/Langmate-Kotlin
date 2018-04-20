package com.langmate.langmate.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.langmate.langmate.Models.RadarModel
import com.langmate.langmate.R
import com.squareup.picasso.Picasso
import java.util.*

/**
 * Created by HP on 2/28/2018.
 */

class RadarAdapter(internal var context: Context, //
                   internal var radarModelArrayList: ArrayList<RadarModel>) : BaseAdapter() {
    internal var radarModel: RadarModel? = null
    private val TAG = "RadarAdapter"

    init {

        inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return radarModelArrayList.size
    }

    override fun getItem(i: Int): RadarModel {

        return radarModelArrayList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val vh: ViewHolder


        if (view == null) {
            view = inflater!!.inflate(R.layout.z_radar_list_item, null)

            vh = ViewHolder()




            vh.personImg = view!!.findViewById(R.id.personImg)
            vh.ofOnLineImg = view.findViewById(R.id.ofOnLineImg)

            vh.name = view.findViewById(R.id.name)
            vh.age = view.findViewById(R.id.age)
            vh.distance = view.findViewById(R.id.distance)
            vh.locationName = view.findViewById(R.id.locationName)

            view.tag = vh

        } else {
            vh = view.tag as ViewHolder
        }


        vh.name!!.text = radarModelArrayList[i].personName + ","
        vh.age!!.text = radarModelArrayList[i].personAge
        vh.distance!!.text = radarModelArrayList[i].personDistance
        vh.locationName!!.text = radarModelArrayList[i].personLocationName



        Picasso.with(this.context).load(radarModelArrayList[i].personImg).into(vh.personImg)





        return view
    }

    class ViewHolder {

        internal var personImg: ImageView? = null
        internal var ofOnLineImg: ImageView? = null
        internal var name: TextView? = null
        internal var age: TextView? = null
        internal var distance: TextView? = null
        internal var locationName: TextView? = null


    }

    companion object {

        private var inflater: LayoutInflater? = null
    }
}
