package com.myasser.edvoraride

import java.net.URL
/*
*Data class to store user information & compute minimum distance with specific ride
* name: user name
* station code: user location in terms of stations
* */
class User(private val name: String, private val stationCode: Int, private val profileURL: String) {
    fun computeMinDistance(r: Ride): Int {
        var minDistance: Int = 0
        for (station in r.getPath()) {
            //iterate to get minimum distance > 0
            val currentDifference = station - this.stationCode
            if (currentDifference < minDistance && currentDifference >= 0) //to avoid negative distance
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