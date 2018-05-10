package com.langmate.langmate.Utilities

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.langmate.langmate.AppConstants.AppConstants

/**
 * Created by HP on 2/27/2018.
 */

public class SharedPrefsUtils {



    companion object {

        lateinit var sp: SharedPreferences
        private val userDocId_:String = "userDocId_"
        private val userName_:String = "userName_"





        fun setUserDocId(context: Context, userDocId: String) {
            sp = PreferenceManager.getDefaultSharedPreferences(context)
            sp.edit().putString(userDocId_, userDocId).apply()

        }

        fun getUserDocId(context: Context): String {
            sp = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getString(userDocId_, AppConstants.userId)
        }

        fun setUserName(context: Context, userDocId: String) {
            sp = PreferenceManager.getDefaultSharedPreferences(context)
            sp.edit().putString(userName_, userDocId).apply()

        }

        fun getUserName(context: Context): String {
            sp = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getString(userName_, AppConstants.userName)
        }


    }



}
