package com.langmate.langmate.Activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.langmate.langmate.AppConstants.AppConstants
import com.langmate.langmate.Fragments.DetailsFragment
import com.langmate.langmate.Fragments.PhotosFragment
import com.langmate.langmate.R
import com.squareup.picasso.Picasso
import java.util.*










/**
 * Created by HP on 3/7/2018.
 */

class PersonDetailsActivity : AppCompatActivity() {

    //Tabs
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    //TextView
    internal lateinit var titelName_TV: TextView
    internal lateinit var detailsName_TV: TextView
    internal lateinit var detailscityDis_TV: TextView
    //ImageButton
    internal lateinit var details_like: ImageButton
    internal lateinit var details_msg: ImageButton
    //ImageView
    internal lateinit var ImgViw_details: ImageView
    //FireBase
    internal lateinit var firebaseFirestore: FirebaseFirestore

    //
    internal lateinit var id : String
    internal lateinit var chatRoomName : String


    private val listView: ListView? = null
    private val arrayAdapter: ArrayAdapter<String>? = null
    internal lateinit var list_of_rooms : ArrayList<String>
    private val name: String? = null
    private val root = FirebaseDatabase.getInstance().reference.root


    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_details)

        firebaseFirestore = FirebaseFirestore.getInstance()

        initView()

        getUserData()

        personTabs()


    }

    private fun getUserData() {


        firebaseFirestore.collection("users").document(AppConstants.userDetailsId).addSnapshotListener { documentSnapshots, e ->
            if (e != null) {
                Log.d("onEvent", "Error: " + e.message)
            }

            documentSnapshots.exists()

             id = documentSnapshots.id

            chatRoomName = documentSnapshots.getString("name")
            titelName_TV.setText(documentSnapshots.getString("name"))

            detailsName_TV.setText(documentSnapshots.getString("name") + " " + documentSnapshots.getString("personAge"))
            detailscityDis_TV.setText(documentSnapshots.getString("personLocationName") + " " + documentSnapshots.getString("personDistance"))


            try {
                Picasso.with(this@PersonDetailsActivity).load(documentSnapshots.getString("personImg")).placeholder(R.drawable.cat).into(ImgViw_details)
            } catch (e: Exception) {
            }
        }

    }

    private fun initView() {
        //TextView
        titelName_TV = findViewById(R.id.titelName_TV)
        detailsName_TV = findViewById(R.id.detailsName_TV)
        detailscityDis_TV = findViewById(R.id.detailscityDis_TV)

        //ImageButton
        details_like = findViewById(R.id.details_like)
        details_msg = findViewById(R.id.details_msg)
        details_msg.visibility = View.INVISIBLE

        //ImageView
        ImgViw_details = findViewById(R.id.ImgViw_details)


        //onClick
        details_like.setOnClickListener {

            MainActivity.UpdateLikeUser(AppConstants.userDetailsId );

            details_msg.visibility = View.VISIBLE

        }

        details_msg.setOnClickListener {

            val map = HashMap<String, Any>()
            map.put(chatRoomName, "")
             root.updateChildren(map)

            val ii = Intent(this@PersonDetailsActivity, ChatRoom::class.java)
            startActivity(ii)

        }


        root.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val set = HashSet<String>()
                val i = dataSnapshot.children.iterator()

                while (i.hasNext()) {
                    set.add((i.next() as DataSnapshot).key)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })



    }

    private fun personTabs() {

        viewPager = findViewById(R.id.viewpager_sportDetails)
        setupViewPager(viewPager)
        tabLayout = findViewById(R.id.tabs)
        tabLayout!!.setupWithViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(PhotosFragment(), "PHOTOS")
        adapter.addFragment(DetailsFragment(), "DETAILS")

        viewPager!!.adapter = adapter
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }

}
