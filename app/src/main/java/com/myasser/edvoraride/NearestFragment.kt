package com.myasser.edvoraride

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NearestFragment : Fragment() {

    private lateinit var nearestRides: Array<Ride>
    private val mainUser = MainActivity.user

    companion object {
        lateinit var originalRides: ArrayList<Ride>
        lateinit var recyclerView: RecyclerView
    }

    init {
        originalRides = MainActivity.rides
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //initiate the nearest ride array and sort them based on Distance between a ride's stations and user
        nearestRides = originalRides.sortedWith(compareBy {
            mainUser.computeMinDistance(it)
        }).toTypedArray()

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nearest, container, false)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.nearest_recycler_view)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = RideRecyclerView(nearestRides.toCollection(ArrayList()), mainUser)

        // Inflate the layout for this fragment
        return view
    }
}