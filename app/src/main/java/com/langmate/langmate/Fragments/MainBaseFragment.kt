package com.langmate.langmate.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log

import com.langmate.langmate.Activities.MainBaseActivity

/**
 * Created by HP on 2/27/2018.
 */

open class MainBaseFragment : Fragment() {
    lateinit var Activity: MainBaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "oncreate>>")
        Activity = this.activity as MainBaseActivity
    }

    override fun onResume() {
        Log.d(TAG, "onResume" + this.javaClass.name)
        super.onResume()
        (activity as MainBaseActivity).BaseFragment = this
    }

    companion object {
        private val TAG = "MainBaseFragment"
    }
}
