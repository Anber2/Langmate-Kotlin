package com.langmate.langmate.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.langmate.langmate.Fragments.*
import com.langmate.langmate.R
import com.yalantis.library.Koloda






/**
 * Created by HP on 2/27/2018.
 */

class MainActivity : MainBaseActivity() {

    internal lateinit var liner_people: LinearLayout
    internal lateinit var liner_match: LinearLayout
    internal lateinit var liner_chats: LinearLayout
    internal lateinit var liner_news: LinearLayout
    internal lateinit var liner_profile: LinearLayout
    internal lateinit var logout_txts: TextView
    //FireBase
    internal lateinit var buttonLogOut: Button
    internal lateinit var mAuth: FirebaseAuth
    internal lateinit var mAuthListener: FirebaseAuth.AuthStateListener


    public override fun onStart() {
        super.onStart()
        //mAuth.addAuthStateListener(mAuthListener)

        if (mAuth.currentUser == null) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initView()
        mAuth = FirebaseAuth.getInstance()

      /*  mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser == null) {

                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
             }
        }*/


        val pushFrag = MainBaseActivity()

        val hf = HomeFragment()

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
                        .setPositiveButton(getString(R.string.alert_dialog_are_you_sure_msg_yes)) { dialog, id ->

                            finish()
                        }
                        .setNegativeButton(getString(R.string.alert_dialog_are_you_sure_msg_no), null)
                        .show()
            } else
                super.onBackPressed()



        } catch (xx: Exception) {
            Log.e("onBackPressed Exception", "  " + xx)
        }

    }

    companion object {
        val TAG = "MainActivity"
        internal lateinit var topBar_txt: TextView
        internal lateinit var koloda: Koloda

        var db: FirebaseFirestore? = null


        fun UpdateLikeUser(item: String) {

            db = FirebaseFirestore.getInstance();


            val contact = db!!.collection("users").document(item)
            contact.update("isLike", "true").addOnSuccessListener(OnSuccessListener<Void> {
                Toast.makeText(mainBaseActivity,"Like Updated.", Toast.LENGTH_SHORT).show()
            })

        }

        fun UpdateDisLikeUser(item: String) {

            db = FirebaseFirestore.getInstance();


            val contact = db!!.collection("users").document(item)
            contact.update("isLike", "false").addOnSuccessListener(OnSuccessListener<Void> {
                Toast.makeText(mainBaseActivity,"Dislike Updated.", Toast.LENGTH_SHORT).show()
            })

        }


    }

    private fun initView() {

        liner_people = findViewById(R.id.liner_people)
        liner_match = findViewById(R.id.liner_match)
        liner_chats = findViewById(R.id.liner_chats)
        liner_news = findViewById(R.id.liner_news)
        liner_profile = findViewById(R.id.liner_profile)
        topBar_txt = findViewById(R.id.topBar_txt)
        //TextView
        logout_txts = findViewById(R.id.logout_txts)

        //on Click
        liner_people.setOnClickListener(View.OnClickListener {

            val hf = HomeFragment()

            // pushFrag.pushFragments(HomeFragment(), false, true)
            val manager = supportFragmentManager
            val backStateName = hf.javaClass.name
            val ft = manager.beginTransaction()
            ft.setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left)
            ft.replace(R.id.fragment_container, hf, backStateName)
            ft.addToBackStack(backStateName)

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
            ft.addToBackStack(backStateName)

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
            ft.addToBackStack(backStateName)

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
            ft.addToBackStack(backStateName)

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
            ft.addToBackStack(backStateName)

            ft.commit()

        })

        logout_txts.setOnClickListener {
            //FirebaseAuth.getInstance().signOut()

            mAuth.signOut()
            finish()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))



        }

    }


}
