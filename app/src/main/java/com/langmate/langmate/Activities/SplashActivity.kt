package com.langmate.langmate.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.langmate.langmate.R

/**
 * Created by HP on 4/21/2018.
 */

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        Handler().postDelayed({
            if (FirebaseAuth.getInstance().currentUser == null)
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            else
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))

            finish()
        }, 3000)


    }
}
