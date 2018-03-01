package com.langmate.langmate.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import com.langmate.langmate.R

/**
 * Created by HP on 2/28/2018.
 */

class RegisterActivity : AppCompatActivity() {

    internal lateinit var ingView_FbLogin: ImageView
    internal lateinit var ingView_goglLogin: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //initView();

        ingView_FbLogin = findViewById(R.id.ingView_FbLogin)
        ingView_goglLogin = findViewById(R.id.ingView_goglLogin)

        ingView_FbLogin.setOnClickListener { Toast.makeText(this@RegisterActivity, "Registering with Facebook", Toast.LENGTH_SHORT).show() }

        ingView_goglLogin.setOnClickListener { Toast.makeText(this@RegisterActivity, "Registering with Google", Toast.LENGTH_SHORT).show() }

    }

}
