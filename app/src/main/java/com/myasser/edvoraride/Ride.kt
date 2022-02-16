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
class Ride(val id:String, private val path:Array<Int>, val date:Date, val map: URL, val state:String, val city:String) {

    fun getPath():Array<Int>{return path}
    fun getOriginStation():Int{return path[0]}
    fun getDestinationStation():Int{return path[path.size-1]}
}