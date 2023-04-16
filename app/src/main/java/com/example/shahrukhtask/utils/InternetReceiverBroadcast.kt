package com.example.shahrukhtask.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class InternetReceiverBroadcast : BroadcastReceiver() {
    var listener: InternetConnectionListener? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            val isConnected = activeNetwork != null && activeNetwork.isConnected
            listener?.onInternetConnectionChanged(isConnected)
        }
    }
}
interface InternetConnectionListener {
    fun onInternetConnectionChanged(isConnected: Boolean)
}
