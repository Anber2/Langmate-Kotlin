package com.langmate.langmate.Activities

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.langmate.langmate.Fragments.*
import com.langmate.langmate.R

/**
 * Created by HP on 2/27/2018.
 */

class MainActivity : MainBaseActivity() {

    internal lateinit var liner_people: LinearLayout
    internal lateinit var liner_match: LinearLayout
    internal lateinit var liner_chats: LinearLayout
    internal lateinit var liner_news: LinearLayout
    internal lateinit var liner_profile: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initView()


        val pushFrag = MainBaseActivity()

        val hf = HomeFragment()

        // pushFrag.pushFragments(HomeFragment(), false, true)
        val manager = supportFragmentManager
        val backStateName = hf.javaClass.name
        val ft = manager.beginTransaction()

        ft.setCustomAnimations(R.anim.slide_in_right,
                R.anim.slide_out_left)
        ft.replace(R.id.fragment_container, hf, backStateName)

        ft.commit()
    }


    override fun onBackPressed() {

        try {
            val fragments = supportFragmentManager.backStackEntryCount

            if (fragments == 0) {
                AlertDialog.Builder(this)
                        .setMessage(getString(R.string.alert_dialog_are_you_sure_msg))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.alert_dialog_are_you_sure_msg_yes)) { dialog, id -> this@MainActivity.finish() }
                        .setNegativeButton(getString(R.string.alert_dialog_are_you_sure_msg_no), null)
                        .show()
            } else

                super.onBackPressed()
        } catch (xx: Exception) {
        }

    }

    companion object {
        val TAG = "MainActivity"
        internal lateinit var topBar_txt: TextView


    }

    private fun initView() {

        liner_people = findViewById(R.id.liner_people)
        liner_match = findViewById(R.id.liner_match)
        liner_chats = findViewById(R.id.liner_chats)
        liner_news = findViewById(R.id.liner_news)
        liner_profile = findViewById(R.id.liner_profile)
        topBar_txt = findViewById(R.id.topBar_txt)

        liner_people.setOnClickListener(View.OnClickListener {

            val hf = HomeFragment()

            // pushFrag.pushFragments(HomeFragment(), false, true)
            val manager = supportFragmentManager
            val backStateName = hf.javaClass.name
            val ft = manager.beginTransaction()

            ft.setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left)
            ft.replace(R.id.fragment_container, hf, backStateName)

            ft.commit()

        })

        liner_match.setOnClickListener(View.OnClickListener {

            val hf = MatchFragment()

            // pushFrag.pushFragments(HomeFragment(), false, true)
            val manager = supportFragmentManager
            val backStateName = hf.javaClass.name
            val ft = manager.beginTransaction()

            ft.setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left)
            ft.replace(R.id.fragment_container, hf, backStateName)

            ft.commit()

        })

        liner_chats.setOnClickListener(View.OnClickListener {

            val hf = ChatsFragment()

            // pushFrag.pushFragments(HomeFragment(), false, true)
            val manager = supportFragmentManager
            val backStateName = hf.javaClass.name
            val ft = manager.beginTransaction()

            ft.setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left)
            ft.replace(R.id.fragment_container, hf, backStateName)

            ft.commit()

        })

        liner_news.setOnClickListener(View.OnClickListener {

            val hf = NewsFragment()

            // pushFrag.pushFragments(HomeFragment(), false, true)
            val manager = supportFragmentManager
            val backStateName = hf.javaClass.name
            val ft = manager.beginTransaction()

            ft.setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left)
            ft.replace(R.id.fragment_container, hf, backStateName)

            ft.commit()

        })

        liner_profile.setOnClickListener(View.OnClickListener {

            val hf = ProfileFragment()

            // pushFrag.pushFragments(HomeFragment(), false, true)
            val manager = supportFragmentManager
            val backStateName = hf.javaClass.name
            val ft = manager.beginTransaction()

            ft.setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left)
            ft.replace(R.id.fragment_container, hf, backStateName)

            ft.commit()

        })

    }

}
