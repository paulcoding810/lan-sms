package com.paulcoding.lansms.tools

import android.content.Context
import android.net.wifi.WifiManager
import java.net.InetAddress
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun getIPAddress(context: Context): String? {
    val wifiManager =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val ip = wifiManager.connectionInfo.ipAddress
    return if (ip != 0) {
        InetAddress.getByAddress(
            ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ip).array()
        ).hostAddress
    } else {
        null
    }
}

fun getSubnet(context: Context): String? {
    val ip = getIPAddress(context)
    ip?.let {
        val parts = it.split(".")
        if (parts.size == 4) {
            return parts[0] + "." + parts[1] + "." + parts[2] + "." + "0"
        }
    }
    return null
}