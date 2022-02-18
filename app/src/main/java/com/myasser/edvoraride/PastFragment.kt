package com.myasser.edvoraride

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class PastFragment : Fragment() {
    private lateinit var currentDate: LocalDateTime
    private lateinit var pastRides: ArrayList<Ride>
    companion object{
        lateinit var originalRides:ArrayList<Ride>
        lateinit var recyclerView: RecyclerView
    }
    init {
        originalRides=MainActivity.rides
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pastRides = ArrayList()
        currentDate = LocalDateTime.now()

        //sorted ascending by date
        val sortedOriginal = originalRides.sortedWith(compareBy {
            it.date
        }).toTypedArray()

        for (r in sortedOriginal) //iterate through original rides
            if (r.date.isBefore(currentDate)) //filter based on date
                pastRides.add(r)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_past, container, false)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.past_recycler_view)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = RideRecyclerView(pastRides, MainActivity.user)

        // Inflate the layout for this fragment
        return view
    }
}