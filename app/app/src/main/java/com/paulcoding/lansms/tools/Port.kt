package com.paulcoding.lansms.tools

import java.net.Socket

fun isPortOpen(ip: String, port: Int, timeout: Int = 200): Boolean {
    return try {
        Socket(ip, port).use { true }
    } catch (e: Exception) {
        false
    }
}

fun scanNetwork(subnet: String, port: Int): String? {
    for (i in 1..254) {
        val ip = "$subnet.$i"
        if (isPortOpen(ip, port)) {
            println("Port $port is open on $ip")
            return "$subnet.$i"
        }
    }

    return null
}