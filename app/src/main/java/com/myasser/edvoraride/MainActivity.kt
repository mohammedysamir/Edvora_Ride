package com.myasser.edvoraride

import android.app.Dialog
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import java.time.LocalDateTime
import java.time.Month
import java.util.concurrent.Executors

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    private lateinit var viewPager: ViewPager2
    private lateinit var cityStateMap: LinkedHashMap<String, String> //v: state, k:city
    private var selectedState = ""
    private var selectedCity = ""
    private lateinit var stateList: List<String>
    private lateinit var cityList: List<String>
    private lateinit var stateSpinner: Spinner
    private lateinit var citySpinner: Spinner

    companion object {
        lateinit var user: User

        //handle set of rides available locally
        lateinit var rides: ArrayList<Ride>
    }

    init {
        //initiate rides list
        rides = ArrayList()
        rides.add(
                Ride(
                        "001",
                        arrayOf(23, 42, 45, 48, 56, 60, 77, 81, 93),
                        LocalDateTime.of(2022, Month.FEBRUARY, 13, 16, 33),
                        null,
                        "Maharashtra",
                        "Mumbai"
                )
        )

        rides.add(
                Ride(
                        "002",
                        arrayOf(20, 39, 40, 42, 54, 63, 72, 88, 98),
                        LocalDateTime.of(2022, Month.MARCH, 15, 13, 0),
                        null,
                        "Maharashtra",
                        "Panvel"
                )
        )

        rides.add(
                Ride(
                        "003",
                        arrayOf(13, 25, 41, 48, 59, 64, 75, 81, 91),
                        LocalDateTime.of(2022, Month.MARCH, 1, 9, 0),
                        null,
                        "Maharashtra",
                        "Mumbai"
                )
        )
        rides.add(
                Ride(
                        "004",
                        arrayOf(9, 16, 28, 41, 44, 53, 65, 77, 96),
                        LocalDateTime.of(2022, Month.FEBRUARY, 17, 12, 30),
                        null,
                        "Maharashtra",
                        "Panvel"
                )
        )
        rides.add(
                Ride(
                        "005",
                        arrayOf(10, 21, 33, 44, 63, 69, 75, 87, 98),
                        LocalDateTime.of(2022, Month.MARCH, 3, 8, 30),
                        null,
                        "Manipur",
                        "Imphal West"
                )
        )
        rides.add(
                Ride(
                        "006",
                        arrayOf(3, 19, 34, 47, 52, 65, 75, 82, 98),
                        LocalDateTime.of(2022, Month.FEBRUARY, 28, 7, 15),
                        null,
                        "Manipur",
                        "Chandel"
                )
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initialize used maps and lists
        cityStateMap = LinkedHashMap()
        stateList = ArrayList()
        cityList = ArrayList()

        user = User(
                "Dhruv Singh",
                40,
                "https://images.unsplash.com/photo-1603415526960-f7e0328c63b1?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8cHJvZmlsZSUyMHBob3RvfGVufDB8fDB8fA%3D%3D&w=1000&q=80"
        )
        initUserData(user)

        findViewById<ImageButton>(R.id.filter).setOnClickListener(this)

        //setup tabLayout and viewPager
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById(R.id.view_pager)
        //1. create list of fragments
        val fragmentList: ArrayList<Fragment> = arrayListOf(
                NearestFragment(),
                UpcomingFragment(),
                PastFragment()
        )
        //2. create adapter for the viewPager and pass list of fragments to it
        viewPager.adapter = ViewPagerAdapter(fragmentList, this@MainActivity)

        //to integrate tabLayout with viewPager
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        tabLayout.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab?.position!!
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab?.position!!
            }

        })


        //fill city state map with provided cities and states
        for (r in rides)
            cityStateMap[r.city] = r.state
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) //if we are at first fragment then quit the app
            super.onBackPressed()
        else //else go back to previous fragment
            viewPager.currentItem = viewPager.currentItem - 1
    }

    private fun initUserData(user: User) {
        findViewById<TextView>(R.id.user_name).text = user.getUserName()
        val executor = Executors.newSingleThreadExecutor()
        executor.execute{
            val `in` = java.net.URL(user.getUserProfile()).openStream()
            val imageBitmap= BitmapFactory.decodeStream(`in`)
            findViewById<ImageView>(R.id.user_image).setImageBitmap(imageBitmap)
        }

        // Glide.with(this).load(user.getUserProfile()).into(findViewById<ImageView>(R.id.user_image))
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.filter -> {
                //display filter dialog
                val dialog = Dialog(p0.context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.filter_dialog)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                //identify spinners
                stateSpinner = dialog.findViewById(R.id.state_spinner)
                citySpinner = dialog.findViewById(R.id.city_spinner)
                //assign on selected listeners
                stateSpinner.onItemSelectedListener = this
                citySpinner.onItemSelectedListener = this
                //get each spinner arrays
                stateSpinner.adapter = ArrayAdapter(this, R.layout.custom_spinner_item, cityStateMap.values.toTypedArray().distinct())
                citySpinner.adapter = ArrayAdapter(this, R.layout.custom_spinner_item, cityStateMap.keys.toTypedArray().distinct())

                dialog.show()
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, v: View?, position: Int, id: Long) {
        if (stateList.isEmpty() && cityList.isEmpty()) {
            stateList = ArrayList<String>(cityStateMap.values).distinct()
            cityList = ArrayList<String>(cityStateMap.keys).distinct()

            if (selectedCity.isEmpty()) //select first city by default
                selectedCity = cityList.first()
            if (selectedState.isEmpty())//select first state by default
                selectedState = stateList.first()
        }

        when (p0?.id) {
            R.id.state_spinner -> {
                selectedState = stateList[position]
                val updatedCityList = cityStateMap.filterValues { it == selectedState }.keys.toTypedArray().distinct()//filter cities based on selected state
                citySpinner.adapter = ArrayAdapter(p0.context, R.layout.custom_spinner_item, updatedCityList)
                cityList = updatedCityList
            }
            R.id.city_spinner -> {
                selectedCity = cityList[position]
            }
        }

        // notify fragment to update recyclerview content
        val filteredRides = rides.filter {
            it.city == selectedCity && it.state == selectedState
        } as ArrayList<Ride>

        //Notify each fragment .. couldn't find more generic way :'(
        NearestFragment.apply {
            originalRides = filteredRides
            recyclerView.adapter = RideRecyclerView(filterRidesByDate(filteredRides, true), user)
        }
        if (viewPager.currentItem == 1) {
            UpcomingFragment.apply {
                recyclerView.adapter = RideRecyclerView(
                        filterRidesByDate(filteredRides, true), user)
            }
        }
        if (viewPager.currentItem == 2) {
            PastFragment.apply {
                recyclerView.adapter = RideRecyclerView(filterRidesByDate(filteredRides, false), user)
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    private fun filterRidesByDate(filtered: ArrayList<Ride>, isAfter: Boolean): ArrayList<Ride> {
        val finalRides = ArrayList<Ride>()
        val tempRides = filtered.sortedWith(compareBy {
            it.date
        }).toTypedArray()

        for (r in tempRides) //iterate through sorted original rides
            if (isAfter && (r.date.isAfter(LocalDateTime.now()) || r.date == LocalDateTime.now())) //filter based on date
                finalRides.add(r)
            else if (!isAfter && (r.date.isBefore(LocalDateTime.now())))
                finalRides.add(r)
        return finalRides
    }
}