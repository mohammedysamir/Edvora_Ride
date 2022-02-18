package com.myasser.edvoraride

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(private val fragments: ArrayList<Fragment>, activity: MainActivity) :
        FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return fragments.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}