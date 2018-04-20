package com.langmate.langmate.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import com.google.firebase.firestore.FirebaseFirestore
import com.langmate.langmate.Activities.PersonDetailsActivity
import com.langmate.langmate.Adapters.RadarAdapter
import com.langmate.langmate.Models.RadarModel
import com.langmate.langmate.R
import java.util.*

/**
 * Created by HP on 3/31/2018.
 */

class RadarFragment : MainBaseFragment() {

    internal lateinit var v: View

    //GridView
    internal lateinit var gridView_Radar: GridView

    //RadarItems
    internal lateinit var radarModelArrayList: ArrayList<RadarModel>
    internal var radarModel: RadarModel? = null
    internal lateinit var radarAdapter: RadarAdapter

    //FireBase
    internal lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater!!.inflate(R.layout.home_fragment, container, false)

        firebaseFirestore = FirebaseFirestore.getInstance()

        initView(v)


        getRadarItems()

        return v
    }

    private fun getRadarItems() {

        radarModelArrayList = ArrayList()

        firebaseFirestore.collection("users").addSnapshotListener { documentSnapshots, e ->
            if (e != null) {
                Log.d("onEvent", "Error: " + e.message)
            }

            for (doc in documentSnapshots) {

                val name = doc.getString("name")
                val personAge = doc.getString("personAge")
                val personDistance = doc.getString("personDistance")
                val personId = doc.getString("personId")
                val personImg = doc.getString("personImg")
                val personLocationName = doc.getString("personLocationName")

                radarModelArrayList.add(RadarModel(personId, "", personImg, name, personAge, "", personDistance, personLocationName))
            }
        }

        /* radarModelArrayList.add(new RadarModel("0", "", "", "Name 1", "40", "", "20 mi", "(Tokyo)"));
        radarModelArrayList.add(new RadarModel("1", "", "", "Name 2", "40", "", "20 mi", "(Tokyo)"));
        radarModelArrayList.add(new RadarModel("2", "", "", "Name 3", "40", "", "20 mi", "(Tokyo)"));
        radarModelArrayList.add(new RadarModel("3", "", "", "Name 4", "40", "", "20 mi", "(Tokyo)"));
        radarModelArrayList.add(new RadarModel("4", "", "", "Name 5", "40", "", "20 mi", "(Tokyo)"));*/


        radarAdapter = RadarAdapter(context, radarModelArrayList)

        gridView_Radar.adapter = radarAdapter

    }

    private fun initView(v: View) {

        //GridView
        gridView_Radar = v.findViewById(R.id.gridView_Radar)
        gridView_Radar.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val ii = Intent(Activity, PersonDetailsActivity::class.java)
            startActivity(ii)
        }

    }
}
