package com.myasser.edvoraride

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDateTime
import java.time.Month

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager2

    companion object {
        lateinit var user: User

        //handle set of rides available locally
        lateinit var rides: ArrayList<Ride>
    }

    init {
        //initiate rides list
        rides = ArrayList<Ride>()
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

        user = User(
                "Dhruv Singh",
                40,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2eN8ZS-WW7HqmiOGKHDdLV8qUEKOU5b3bZg&usqp=CAU"
        )
        initUserData(user)

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
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) //if we are at first fragment then quit the app
            super.onBackPressed()
        else //else go back to previous fragment
            viewPager.currentItem = viewPager.currentItem - 1
    }

    private fun initUserData(user: User) {
        findViewById<TextView>(R.id.user_name).text = user.getUserName()
        //TODO: be able to initiate user's image from URL
        // Glide.with(this).load(user.getUserProfile()).into(findViewById<ImageView>(R.id.user_image))
    }
}