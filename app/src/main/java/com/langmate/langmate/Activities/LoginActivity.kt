package com.langmate.langmate.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.langmate.langmate.AppConstants.AppConstants
import com.langmate.langmate.R
import com.langmate.langmate.Utilities.SharedPrefsUtils
import java.util.*


/**
 * Created by HP on 2/28/2018.
 */

class LoginActivity : AppCompatActivity() {


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
    private val RC_SIGN_IN = 2
    internal lateinit var mAuthListener: FirebaseAuth.AuthStateListener

    //facebook
    internal lateinit var callbackManager: CallbackManager
    private val EMAIL = "email"
    internal lateinit var loginButton: LoginButton


    //add new user
    var db: FirebaseFirestore? = null
    val docData = HashMap<String, Any>()
    val nestedData = HashMap<String, Any>()
    val nestedData2 = HashMap<String, Any>()


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_fragment)

        callbackManager = CallbackManager.Factory.create()
        db = FirebaseFirestore.getInstance();

        initView()
        setUpGoogleSignIn()


        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    override fun onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                    }

                    override fun onError(exception: FacebookException) {
                        Log.d(TAG, "facebook:onError", exception);
                    }
                })


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
        //Facebook
        loginButton = findViewById(R.id.facebook_login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));

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

        googleBtn.setOnClickListener { signIn() }


        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // App code
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })


    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

        callbackManager.onActivityResult(requestCode, resultCode, data);


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
                        AppConstants.userId = user!!.uid!!

                        SharedPrefsUtils.setUserDocId(this@LoginActivity, user!!.uid!!)
                        SharedPrefsUtils.setUserName(this@LoginActivity, user!!.displayName!!)


                        val isNew = task.getResult().getAdditionalUserInfo().isNewUser()

                        if (isNew) {

                            Log.d(TAG, "onComplete: " + if (isNew) "new user" else "old user")

                            docData.put("name", user!!.displayName!!);
                            docData.put("personAge", "00");
                            docData.put("personDistance", "00");
                            docData.put("personId", "00");
                            docData.put("personImg", "https://cdn1.iconfinder.com/data/icons/business-charts/512/customer-256.png");
                            docData.put("personLocationName", "unknown");

                            //nestedData2
                            nestedData2.put("date", "")
                            nestedData2.put("name", "")
                            nestedData2.put("personAge", " ")
                            nestedData2.put("personDistance", " ")
                            nestedData2.put("personId", "")
                            nestedData2.put("personImg", "")
                            nestedData2.put("personLocationName", "")



                            db!!.collection("users").document(user!!.uid!!)
                                    .set(docData)
                                    .addOnSuccessListener {

                                        Log.d(TAG, "DocumentSnapshot successfully written! " + taskId)

                                    }
                                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                          db!!.collection("users").document(user!!.uid!!).collection("likedMe").document().set(nestedData2)
                                    .addOnSuccessListener {
                                        Log.d(TAG, "likedMe successfully written!")


                                    }
                                    .addOnFailureListener {

                                        e ->
                                        Log.w(TAG, "Error writing likedMe", e)
                                    }


                        }


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

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:" + token)

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                    override fun onComplete(task: Task<AuthResult>) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success")
                            val user = mAuth.getCurrentUser()


                            //updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException())
                            Toast.makeText(this@LoginActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                            //  updateUI(null)
                        }

                        // ...
                    }
                })
    }


}


