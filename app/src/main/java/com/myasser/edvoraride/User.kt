package com.myasser.edvoraride

import kotlin.math.abs

/*
*Data class to store user information & compute minimum distance with specific ride
* name: user name
* station code: user location in terms of stations
* */
class User(private val name: String, private val stationCode: Int, private val profileURL: String) {
    fun computeMinDistance(r: Ride): Int {
        var minDistance = Int.MAX_VALUE
        for (station in r.getPath()) {
            val currentDifference = abs(station - this.stationCode)
            if (currentDifference <= minDistance)
                minDistance = currentDifference
        }
        return minDistance
    }

    fun getUserName(): String {
        return name
    }

    fun getUserStation(): Int {
        return stationCode
    }

    fun getUserProfile(): String {
        return profileURL
    }
}