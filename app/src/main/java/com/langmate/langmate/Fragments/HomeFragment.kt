package com.langmate.langmate.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.langmate.langmate.Activities.MainActivity
import com.langmate.langmate.Adapters.RadarAdapter
import com.langmate.langmate.Models.RadarModel
import com.langmate.langmate.R
import java.util.*

/**
 * Created by HP on 2/27/2018.
 */

class HomeFragment : MainBaseFragment() {

    internal lateinit var v: View

    //GridView
    internal lateinit var gridView_Radar: GridView

    internal lateinit var radarModelArrayList: ArrayList<RadarModel>
    internal var radarModel: RadarModel? = null
    internal lateinit var radarAdapter: RadarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater!!.inflate(R.layout.home_fragment, container, false)


        initView(v)


        getRadarItems()

        return v
    }

    private fun getRadarItems() {
        radarModelArrayList = ArrayList()

        radarModelArrayList.add(RadarModel("0", "", "", "Name 1", "40", "", "20 mi", "(Tokyo)"))
        radarModelArrayList.add(RadarModel("1", "", "", "Name 2", "40", "", "20 mi", "(Tokyo)"))
        radarModelArrayList.add(RadarModel("2", "", "", "Name 3", "40", "", "20 mi", "(Tokyo)"))
        radarModelArrayList.add(RadarModel("3", "", "", "Name 4", "40", "", "20 mi", "(Tokyo)"))
        radarModelArrayList.add(RadarModel("4", "", "", "Name 5", "40", "", "20 mi", "(Tokyo)"))


        radarAdapter = RadarAdapter(context, radarModelArrayList)

        gridView_Radar.adapter = radarAdapter

    }

    private fun initView(v: View) {

        MainActivity.topBar_txt.visibility = View.INVISIBLE


        //GridView
        gridView_Radar = v.findViewById(R.id.gridView_Radar)


    }
}
