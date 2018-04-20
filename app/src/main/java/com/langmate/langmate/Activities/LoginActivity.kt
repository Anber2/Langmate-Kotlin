package com.langmate.langmate.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.langmate.langmate.R
import com.langmate.langmate.Utilities.SharedPrefsUtils


/**
 * Created by HP on 2/28/2018.
 */

class LoginActivity : AppCompatActivity() , GoogleApiClient.OnConnectionFailedListener {


    internal var v: View? = null


    internal lateinit var textView_register: TextView
    internal lateinit var textView_skip: TextView
    internal lateinit var ingView_FbLogin: ImageView
    internal lateinit var ingView_goglLogin: ImageView

    //google SignIn
    internal lateinit var googleBtn: SignInButton
    internal lateinit var mGoogleApiClient : GoogleApiClient
    internal var RC_SIGN_IN: Int = 1
    internal lateinit var  mAuth: FirebaseAuth
    internal lateinit var  mGoogleSignInClient : GoogleSignInClient



    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_fragment)

        mAuth = FirebaseAuth.getInstance();

        initView()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        mGoogleApiClient = GoogleApiClient.Builder(this).enableAutoManage(this,this ).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build()
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
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

         if (requestCode == RC_SIGN_IN) {
            /* val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
             handleSignInResult(result)*/

           /*  val task = GoogleSignIn.getSignedInAccountFromIntent(data)

             handleSignInResult(task)*/
             if (requestCode === RC_SIGN_IN) {
                 val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                 try {
                     // Google Sign In was successful, authenticate with Firebase
                     val account = task.getResult(ApiException::class.java)
                     firebaseAuthWithGoogle(account)
                 } catch (e: ApiException) {
                     // Google Sign In failed, update UI appropriately
                     Log.w("ss", "Google sign in failed", e)
                  }

             }

         }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("dd", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("sss", "signInWithCredential:success")
                        val user = mAuth.getCurrentUser()
                        SharedPrefsUtils.setUserSignedIn(this@LoginActivity,
                                true)



                        val googleName = acct.getDisplayName()
                        Log.w("getDisplayName", googleName)

                        val i = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(i)

                        Toast.makeText(this@LoginActivity, "HELLO " + googleName, Toast.LENGTH_SHORT).show()


                        finish()
                    } else {
                         Log.w("ssaa", "signInWithCredential:failure", task.exception)
                        Toast.makeText(this@LoginActivity, "Login failure", Toast.LENGTH_SHORT).show()
                     }

                    // ...
                })
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {

        try {
            val account = completedTask.getResult(ApiException::class.java)

           val googleName = account.getDisplayName()
            val  googleEmail = account.getEmail()
            val  googleId = account.getId()

            SharedPrefsUtils.setUserSignedIn(this@LoginActivity,
                    true)



            // Signed in successfully, show authenticated UI.
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("SignInResult--", "signInResult:failed code=" + e.statusCode)
        }


    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStart() {
        super.onStart()


        val account = GoogleSignIn.getLastSignedInAccount(this)


    }
}


