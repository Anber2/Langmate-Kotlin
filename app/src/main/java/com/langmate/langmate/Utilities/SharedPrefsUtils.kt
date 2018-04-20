package com.langmate.langmate.Utilities

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by HP on 2/27/2018.
 */

public class SharedPrefsUtils {

    private val TAG = "PreferenceUtil"
    lateinit var sp: SharedPreferences

    private val IS_USER_SIGNED_IN = "is_user_signed_in"

    companion object {

        lateinit var sp: SharedPreferences
        private val IS_USER_SIGNED_IN = "is_user_signed_in"

        fun setUserSignedIn(context: Context, signed_in: Boolean) {
            sp = PreferenceManager.getDefaultSharedPreferences(context)
            sp.edit().putBoolean(IS_USER_SIGNED_IN, signed_in).apply()

        }

        fun isUserSignedIn(context: Context): Boolean {
            sp = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getBoolean(IS_USER_SIGNED_IN, false)
        }


    }


    fun setUserSignedIn(context: Context, signed_in: Boolean) {
        sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putBoolean(IS_USER_SIGNED_IN, signed_in).apply()

    }

    fun isUserSignedIn(context: Context): Boolean {
        sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getBoolean(IS_USER_SIGNED_IN, false)
    }
}
