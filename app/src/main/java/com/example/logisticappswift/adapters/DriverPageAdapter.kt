package com.example.logisticappswift.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.logisticappswift.fragments.*

class DriverPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return DriverHomeFragment()
            1 -> return DriverActivityFragment()
            2 -> return SettingFragment()
            else  -> DriverHomeFragment()
        }

        return DriverHomeFragment()
    }
}