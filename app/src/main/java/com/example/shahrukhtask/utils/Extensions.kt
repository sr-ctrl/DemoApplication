package com.example.shahrukhtask.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

object Extensions  {

    fun View.showSnackBar(text:String){
        Snackbar.make(this,text, Snackbar.LENGTH_LONG).show()
    }

    fun Context.showToast(text:String){
        Toast.makeText(this,text, Toast.LENGTH_SHORT).show()
    }
}