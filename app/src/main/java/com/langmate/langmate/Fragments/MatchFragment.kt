package com.langmate.langmate.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageButton
import android.widget.RelativeLayout
import com.langmate.langmate.Activities.MainActivity.Companion.topBar_txt
import com.langmate.langmate.Adapters.MatchAdapter
import com.langmate.langmate.Adapters.RadarAdapter
import com.langmate.langmate.Models.RadarModel
import com.langmate.langmate.R
import com.yalantis.library.Koloda
import com.yalantis.library.KolodaListener
import kotlinx.android.synthetic.main.match_fragment.*
import java.util.*

/**
 * Created by HP on 3/1/2018.
 */
public class MatchFragment : MainBaseFragment() {

    internal lateinit var v: View

    internal lateinit var koloda: Koloda

    internal lateinit var dislike: ImageButton
    internal lateinit var like: ImageButton

    internal lateinit var btons : RelativeLayout


    private var adapter: MatchAdapter? = null


    internal lateinit var radarModelArrayList: ArrayList<RadarModel>
    internal var radarModel: RadarModel? = null
    internal lateinit var radarAdapter: RadarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater!!.inflate(R.layout.match_fragment, container, false)

        topBar_txt.visibility = View.INVISIBLE

        koloda = v. findViewById(R.id.koloda)

        dislike = v. findViewById(R.id.dislike)

        like = v. findViewById(R.id.like)
        btons = v. findViewById(R.id.btons)


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
        val data = arrayOf(R.drawable.cat,
                R.drawable.cat,
                R.drawable.cat,
                R.drawable.cat,
                R.drawable.cat
        )
        adapter = MatchAdapter(activity, data.toList())
        koloda.adapter = adapter
        koloda.isNeedCircleLoading = true
    }

    private fun setUpCLickListeners() {
        dislike.setOnClickListener { koloda.onClickLeft() }
        like.setOnClickListener { koloda.onClickRight() }
    }
}