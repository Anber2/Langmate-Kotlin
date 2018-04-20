package com.langmate.langmate.Fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageButton
import android.widget.RelativeLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.langmate.langmate.Activities.MainActivity.Companion.topBar_txt
import com.langmate.langmate.Adapters.MatchAdapter
import com.langmate.langmate.Models.MatcheModel
import com.langmate.langmate.R
import com.yalantis.library.Koloda
import com.yalantis.library.KolodaListener
import kotlinx.android.synthetic.main.match_fragment.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


/**
 * Created by HP on 3/1/2018.
 */
public class MatchFragment : MainBaseFragment() {

    internal lateinit var v: View

    internal lateinit var koloda: Koloda

    internal lateinit var dislike: ImageButton
    internal lateinit var like: ImageButton

    internal lateinit var btons: RelativeLayout


    private var adapter: MatchAdapter? = null


    internal lateinit var matcheImg: ArrayList<MatcheModel>

    internal lateinit var matcheImgInt: ArrayList<Drawable>


    //FireBase
    internal lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater!!.inflate(R.layout.match_fragment, container, false)

        firebaseFirestore = FirebaseFirestore.getInstance()


        topBar_txt.visibility = View.INVISIBLE

        koloda = v.findViewById(R.id.koloda)

        dislike = v.findViewById(R.id.dislike)

        like = v.findViewById(R.id.like)
        btons = v.findViewById(R.id.btons)


        btons.bringToFront()


        initializeDeck()
        fillData()
        setUpCLickListeners()

        return v
    }

    override fun onStart() {
        super.onStart()
        activityMain.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            @SuppressLint("NewApi")
            override fun onGlobalLayout() {
                //now we can retrieve the width and height
                val width = activityMain.width
                val height = activityMain.height
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
                    activityMain.viewTreeObserver.removeOnGlobalLayoutListener(this)
                else
                    activityMain.viewTreeObserver.removeGlobalOnLayoutListener(this)
            }
        })
    }


    /**
     * Initialize Deck and Adapter for filling Deck
     * Also implemented listener for caching requests
     */
    private fun initializeDeck() {

        koloda.kolodaListener = object : KolodaListener {

            internal var cardsSwiped = 0

            override fun onNewTopCard(position: Int) {
                //Toast.makeText(activity, "On new top card", Toast.LENGTH_LONG).show()
            }

            override fun onCardSwipedLeft(position: Int) {
                // Toast.makeText(activity, "On card swiped left", Toast.LENGTH_LONG).show()
            }

            override fun onCardSwipedRight(position: Int) {
                //  Toast.makeText(activity, "On card swiped right", Toast.LENGTH_LONG).show()
            }

            override fun onEmptyDeck() {
                //  Toast.makeText(activity, "On empty deck", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Fills deck with data
     */
    private fun fillData() {

        matcheImg = ArrayList()

        matcheImgInt = ArrayList()

        firebaseFirestore.collection("users").addSnapshotListener { documentSnapshots, e ->
            if (e != null) {
                Log.d("onEvent", "Error: " + e.message)
            }

            for (doc in documentSnapshots) {

                val name = doc.getString("name")
                val personAge = doc.getString("personAge")
                val personDistance = doc.getString("personDistance")
                val personId = doc.getString("personId")
                val personImg = doc.getString("personImg")
                val personLocationName = doc.getString("personLocationName")


                matcheImg.add(MatcheModel(personId, name, personAge, personDistance, personImg, personLocationName))


            }
            adapter = MatchAdapter(activity, matcheImg)
            koloda.adapter = adapter
            koloda.isNeedCircleLoading = true


        }


        /* val data = arrayOf(matcheImgInt,
                 R.drawable.cat
         )*/


        /**/
    }

    private fun setUpCLickListeners() {
        dislike.setOnClickListener { koloda.onClickLeft() }
        like.setOnClickListener { koloda.onClickRight() }
    }

    @Throws(IOException::class)
    fun drawableFromUrl(url: String): Drawable {
        val x: Bitmap

        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connect()
        val input = connection.getInputStream()

        x = BitmapFactory.decodeStream(input)
        return BitmapDrawable(x)
    }
}