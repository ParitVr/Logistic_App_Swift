package com.example.logisticappswift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.logisticappswift.adapters.PageAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_client.*

class ClientActivity : AppCompatActivity() {

    var tab_title = arrayOf("Home", "Create Job", "Setting")
    var home_icon = R.drawable.ic_baseline_home_24
    var add_icon = R.drawable.ic_baseline_add_circle_24
    var setting_icon = R.drawable.ic_baseline_settings_24
    var icon_tab = arrayOf(home_icon, add_icon, setting_icon)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)


        //init
        view_pager.adapter = PageAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(tab_layout, view_pager){
                tab, position ->
            tab.setIcon(icon_tab[position])
        }.attach()
        //

    }

    private fun someFunction(){

    }
}