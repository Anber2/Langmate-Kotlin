package com.langmate.langmate.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.langmate.langmate.R


/**
 * Created by HP on 2/28/2018.
 */

class LoginActivity : AppCompatActivity()   {


    internal var v: View? = null


    internal lateinit var textView_register: TextView
    internal lateinit var textView_skip: TextView
    internal lateinit var ingView_FbLogin: ImageView
    internal lateinit var ingView_goglLogin: ImageView

    //google SignIn
    private val TAG = "LoginActivity"
    internal lateinit var googleBtn: SignInButton
    internal lateinit var mAuth: FirebaseAuth
    internal lateinit var mGoogleSignInClient: GoogleSignInClient
    private  val RC_SIGN_IN = 2
    internal lateinit var mAuthListener: FirebaseAuth.AuthStateListener



    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_fragment)


        initView()
        setUpGoogleSignIn()


    }

    private fun setUpGoogleSignIn() {

        mAuth = FirebaseAuth.getInstance()

       /* mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser != null) {

                startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                finish()
            }
        }*/


        /*       GoogleSignInOptions gso2 = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();*/

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }


    private fun initView() {
        //TextView
        textView_register = findViewById(R.id.textView_register)
        textView_skip = findViewById(R.id.textView_skip)
        //ImageView
        ingView_FbLogin = findViewById(R.id.ingView_FbLogin)
        ingView_goglLogin = findViewById(R.id.ingView_goglLogin)

        //SignInButton
        googleBtn = findViewById(R.id.googleBtn)
        // Configure Google Sign In


// ...

        //on click

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

        googleBtn.setOnClickListener{signIn()}




    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }

        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val user = mAuth.currentUser

                        Toast.makeText(this@LoginActivity, "User Signed In", Toast.LENGTH_SHORT).show();

                        // updateUI(user);
                       /* startActivity(Intent(this@LoginActivity, MainActivity::class.java))*/

                        finish()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                     } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        Toast.makeText(this@LoginActivity, "User authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        // updateUI(null);
                    }

                    // ...
                }
    }

    public override fun onStart() {
        super.onStart()
     //   mAuth.addAuthStateListener(mAuthListener)

        if (mAuth.currentUser != null) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
     }


}


