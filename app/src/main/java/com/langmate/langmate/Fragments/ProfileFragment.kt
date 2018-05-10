package com.langmate.langmate.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.langmate.langmate.Activities.MainActivity
import com.langmate.langmate.Adapters.RadarAdapter
import com.langmate.langmate.Models.RadarModel
import com.langmate.langmate.R
import java.util.*



/**
 * Created by HP on 3/1/2018.
 */
class ProfileFragment : MainBaseFragment() {

    internal lateinit var v: View



    internal lateinit var radarModelArrayList: ArrayList<RadarModel>
    internal var radarModel: RadarModel? = null
    internal lateinit var radarAdapter: RadarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater , container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater!!.inflate(R.layout.profile_fragment, container, false)


        MainActivity.topBar_txt.visibility = View.INVISIBLE




        return v
    }


}