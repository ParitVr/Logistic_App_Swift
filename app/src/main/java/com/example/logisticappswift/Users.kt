package com.example.logisticappswift

import android.app.Application
import kotlinx.android.parcel.Parcelize

public class user_data : Application() {
    companion object{
        var username = ""
        var email = ""
        var status = ""
        var profile_img_url = ""
        var uid = ""
    }
}