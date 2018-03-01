package com.langmate.langmate.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.langmate.langmate.R

/**
 * Created by HP on 2/28/2018.
 */

class LoginActivity : AppCompatActivity() {

    internal var v: View? = null

    internal lateinit var textView_register: TextView
    internal lateinit var textView_skip: TextView
    internal lateinit var ingView_FbLogin: ImageView
    internal lateinit var ingView_goglLogin: ImageView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_fragment)

        initView()


    }


    private fun initView() {
        //TextView
        textView_register = findViewById(R.id.textView_register)
        textView_skip = findViewById(R.id.textView_skip)
        //ImageView
        ingView_FbLogin = findViewById(R.id.ingView_FbLogin)
        ingView_goglLogin = findViewById(R.id.ingView_goglLogin)


        textView_skip.setOnClickListener {
            val i = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }
        textView_register.setOnClickListener {
            val i = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(i)
            finish()
        }

        ingView_FbLogin.setOnClickListener { Toast.makeText(this@LoginActivity, "Login with Facebook", Toast.LENGTH_SHORT).show() }

        ingView_goglLogin.setOnClickListener { Toast.makeText(this@LoginActivity, "Login with Google", Toast.LENGTH_SHORT).show() }

    }

}


