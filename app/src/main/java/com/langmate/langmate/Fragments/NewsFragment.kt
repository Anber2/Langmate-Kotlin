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
import com.langmate.langmate.Activities.MainActivity
import com.langmate.langmate.Activities.PersonDetailsActivity
import com.langmate.langmate.Adapters.RadarAdapter
import com.langmate.langmate.AppConstants.AppConstants
import com.langmate.langmate.Models.RadarModel
import com.langmate.langmate.R
import java.util.*

/**
 * Created by HP on 3/1/2018.
 */
class NewsFragment : MainBaseFragment() {

    internal lateinit var v: View


    //RadarItems

    internal lateinit var radarModelArrayList: ArrayList<RadarModel>
    internal lateinit var docIdArrayList: ArrayList<String>

    internal var radarModel: RadarModel? = null
    internal lateinit var radarAdapter: RadarAdapter

    //GridView
    internal lateinit var gridView_News: GridView

    //FireBase
    internal lateinit var firebaseFirestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater!!.inflate(R.layout.news_fragment, container, false)
        firebaseFirestore = FirebaseFirestore.getInstance()

        MainActivity.topBar_txt.visibility = View.INVISIBLE

        initView(v)

        getLikeMeUsers()


        return v
    }

    private fun getLikeMeUsers() {

        radarModelArrayList = ArrayList()
        docIdArrayList = ArrayList()


        firebaseFirestore.collection("users").document(AppConstants.userId).collection("likedMe").addSnapshotListener { documentSnapshots, e ->
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

                radarModelArrayList.add(RadarModel(doc.id, "", personImg, name, personAge, "", personDistance, personLocationName))


            }

            if (context != null) {
                radarAdapter = RadarAdapter(context!!, radarModelArrayList)

                gridView_News.adapter = radarAdapter

            }
        }






    }



    private fun initView(v: View) {

        //GridView
        gridView_News = v.findViewById(R.id.gridView_News)
        gridView_News.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->

            AppConstants.userDetailsId = radarModelArrayList.get(i).personId

            val ii = Intent(Activity, PersonDetailsActivity::class.java)
            startActivity(ii)
        }

    }

}