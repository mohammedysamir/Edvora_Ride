package com.myasser.edvoraride

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime
import kotlin.collections.ArrayList

class UpcomingFragment : Fragment() {
    private lateinit var currentDate: LocalDateTime
    private lateinit var upcomingRides: ArrayList<Ride>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val originalRides = MainActivity.rides
        upcomingRides = ArrayList()
        currentDate = LocalDateTime.now()
        //sorted ascending by date
        val sortedOriginal = originalRides.sortedWith(compareBy {
            it.date
        }).toTypedArray()

        for (r in sortedOriginal) //iterate through sorted original rides
            if (r.date.isAfter(currentDate)) //filter based on date
                upcomingRides.add(r)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upcoming, container, false)
        val linearLayoutManager = LinearLayoutManager(context)
        val recyclerView = view.findViewById<RecyclerView>(R.id.upcoming_recycler_view)
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.adapter = RideRecyclerView(upcomingRides, MainActivity.user)

        // Inflate the layout for this fragment
        return view
    }
}