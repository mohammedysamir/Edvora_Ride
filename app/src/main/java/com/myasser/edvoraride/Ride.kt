package com.myasser.edvoraride

import java.net.URL
import java.util.*

/**
 * Data class to store Ride information
 * id: identifier for each ride.
 * path: set of stations on the path of the ride.
 * date: date of the ride.
 * map: map URL to show the ride source and destination points
 * state: represents source state of the ride
 * city: represents source city of the ride
 * */
class Ride(id:String, private val path:Array<Int>, date:Date, map: URL, state:String, city:String) {

    fun getOriginStation():Int{return path[0]}
    fun getDestinationStation():Int{return path[path.size-1]}
}