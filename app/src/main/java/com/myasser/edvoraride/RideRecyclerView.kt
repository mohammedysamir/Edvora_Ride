package com.myasser.edvoraride

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RideRecyclerView(private val mList: ArrayList<Ride>, private val user: User) :
        RecyclerView.Adapter<RideRecyclerView.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rideId: TextView = itemView.findViewById(R.id.ride_id)
        val rideOriginStation: TextView = itemView.findViewById(R.id.ride_origin)
        val ridePath: TextView = itemView.findViewById(R.id.ride_path)
        val rideDate: TextView = itemView.findViewById(R.id.ride_date)
        val rideDistance: TextView = itemView.findViewById(R.id.distance)
        val rideCity: TextView = itemView.findViewById(R.id.city_name)
        val rideState: TextView = itemView.findViewById(R.id.state_name)

        //TODO: don't forget to implement the map
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.ride_recycler_view, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mContext = holder.itemView.context
        val ride = mList[position]
        holder.rideCity.text = ride.city //place city name
        holder.rideState.text = ride.state //place state name
        holder.rideId.text = mContext.getString(R.string.ride_id, ride.id) //place ride id
        holder.rideOriginStation.text = mContext.getString(R.string.origin_station, ride.getOriginStation())
        //create string path from the array of stations
        var finalPath = "["
        for (s in ride.getPath()) finalPath += s.toString() + ','
        val length = finalPath.length
        finalPath = finalPath.substring(0, length - 1) + ']'

        holder.ridePath.text = mContext.getString(R.string.station_path, finalPath)
        holder.rideDate.text = mContext.getString(R.string.date,
                LocalDateTime.parse(ride.date.toString(), DateTimeFormatter.ISO_DATE_TIME).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))

        //identify our user and calculate minimum distance
        holder.rideDistance.text =
                mContext.getString(R.string.distance, user.computeMinDistance(ride))
    }

    override fun getItemCount(): Int {
        return mList.count()
    }
}