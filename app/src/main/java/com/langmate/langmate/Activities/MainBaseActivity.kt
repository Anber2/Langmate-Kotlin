package com.langmate.langmate.Activities

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ProgressBar
import com.langmate.langmate.Fragments.HomeFragment
import com.langmate.langmate.Fragments.MainBaseFragment
import com.langmate.langmate.R

/**
 * Created by HP on 2/27/2018.
 */

open class MainBaseActivity : AppCompatActivity() {
    var BaseFragment: MainBaseFragment? = null
    private var spinWheelDialog: Dialog? = null
    internal var spinWheelTimer = Handler() // Handler to post a runnable that

    internal var dismissSpinner: Runnable = Runnable { stopSpinWheel() }

    val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = mainBaseActivity
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager
                    .activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    val currentFragment: String
        get() {
            val fragmentName: String
            val fragmentManager = supportFragmentManager
            Log.e(TAG, ">>>>" + fragmentManager.backStackEntryCount)
            if (fragmentManager.backStackEntryCount == 0) {
                fragmentName = HomeFragment::class.java.name
                Log.e(TAG, "stack count zero" + fragmentName)
            } else {
                val fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).name
                val currentFragment = fragmentManager.findFragmentByTag(fragmentTag)
                fragmentName = currentFragment.javaClass.name
            }
            return fragmentName
        }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "On create")
        mainBaseActivity = this
    }

    fun startSpinwheel(setDefaultLifetime: Boolean, isCancelable: Boolean) {
        // Log.d(TAG, "startSpinwheel"+getCurrentActivity().getClass() );
        // If already showing no need to create.
        if (spinWheelDialog != null && spinWheelDialog!!.isShowing)
            return

        spinWheelDialog = Dialog(mainBaseActivity, R.style.wait_spinner_style)
        val progressBar = ProgressBar(mainBaseActivity)
        val layoutParams = ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
        spinWheelDialog!!.addContentView(progressBar, layoutParams)
        spinWheelDialog!!.setCancelable(isCancelable)

        runOnUiThread { spinWheelDialog!!.show() }

        // start timer for SPINWHEEL_LIFE_TIME
        spinWheelTimer.removeCallbacks(dismissSpinner)
        if (setDefaultLifetime)
        // If requested for default dismiss time.
            spinWheelTimer.postAtTime(dismissSpinner, SystemClock.uptimeMillis() + SPINWHEEL_LIFE_TIME)

        spinWheelDialog!!.setCanceledOnTouchOutside(false)
    }

    fun stopSpinWheel() {
        // Log.d(TAG, "stopSpinWheel"+getCurrentActivity().getClass());
        if (spinWheelDialog != null)
            try {
                spinWheelDialog!!.dismiss()
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, "Parent is died while tryingto dismiss spin wheel dialog ")
                e.printStackTrace()
            }

        spinWheelDialog = null
    }

    private fun isNeedTransaction(backStateName: String): Boolean {
        var needTransaction = true
        if (BaseFragment != null) {
            val baseFrag = BaseFragment!!.javaClass.name
            if (baseFrag == backStateName) {
                needTransaction = false
            } else
                needTransaction = true
        }
        return needTransaction
    }

    fun pushFragments(fragment: Fragment, shouldAnimate: Boolean,
                      shouldAdd: Boolean) {
        val manager = supportFragmentManager
        val backStateName = fragment.javaClass.name

        if (isNeedTransaction(backStateName)) {
            val fragmentPopped = manager.popBackStackImmediate(
                    backStateName, 0)

            if (!fragmentPopped) { // fragment not in back stack, create it.
                val ft = manager.beginTransaction()
                if (shouldAnimate)
                    ft.setCustomAnimations(R.anim.slide_in_right,
                            R.anim.slide_out_left)
                ft.replace(R.id.fragment_container, fragment, backStateName)
                if (shouldAdd)
                    ft.addToBackStack(backStateName)
                ft.commit()
                manager.executePendingTransactions()
            }
        }
    }

    companion object {
        lateinit var mainBaseActivity: MainBaseActivity
            protected set
        private val TAG = "MainBaseActivity"

        val SPINWHEEL_LIFE_TIME = 700
    }


}
