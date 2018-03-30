package com.langmate.langmate.Activities

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.langmate.langmate.Fragments.DetailsFragment
import com.langmate.langmate.Fragments.PhotosFragment
import com.langmate.langmate.R
import java.util.*

/**
 * Created by HP on 3/7/2018.
 */

class PersonDetailsActivity : AppCompatActivity() {

    //Tabs
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_details)


        personTabs()


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
