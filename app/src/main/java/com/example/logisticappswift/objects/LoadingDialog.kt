package com.example.logisticappswift.objects

import android.app.Activity
import android.app.AlertDialog
import com.example.logisticappswift.R

class LoadingDialog(val activity : Activity) {
    private lateinit var isdialog :AlertDialog
    fun startLoading(){
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.progress_dialog, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isdialog = builder.create()
        isdialog.show()
    }
    fun isDismiss(){
        isdialog.dismiss()
    }
}