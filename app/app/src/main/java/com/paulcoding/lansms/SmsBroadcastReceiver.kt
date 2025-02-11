package com.paulcoding.lansms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class SmsBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val bundle = intent.extras
            val pdus: Array<ByteArray> = bundle?.get("pdus") as? Array<ByteArray>
                ?: return
            val messages: Array<SmsMessage?> = arrayOfNulls(pdus.size)
            val sb = StringBuilder()
            for (i in pdus.indices) {
                messages[i] = SmsMessage.createFromPdu(pdus[i], SmsMessage.FORMAT_3GPP2)
                sb.append(messages[i]?.messageBody ?: "")
            }
            val sender: String = messages[0]?.originatingAddress.toString()
            val message = sb.toString()
            println("[$sender] $message")
            val prefsManager = PrefsManager(context)
            val ip = prefsManager.getIP()
            if (ip.isNotEmpty()) {
                sendMessageToLAN("[$sender] $message", ip)
            }
        }
    }
}

fun sendMessageToLAN(message: String, ipAddress: String, port: Int = PORT) {
    try {
        val socket = DatagramSocket()
        val buffer = message.toByteArray()
        val address = InetAddress.getByName(ipAddress)
        val packet = DatagramPacket(buffer, buffer.size, address, port)

        CoroutineScope(Dispatchers.IO).launch {
            socket.send(packet)
            socket.close()
        }

        println("Message sent to $ipAddress:$port")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}