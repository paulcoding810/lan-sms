# Lan SMS

A utility that forwards SMS messages from your Android phone to your laptop over LAN, making it convenient to receive OTP codes and other messages without checking your phone.

## How It Works

1. The Android app runs in the background and listens for incoming SMS using `BroadcastReceiver`
2. When a message is received (`Telephony.Sms.Intents.SMS_RECEIVED_ACTION`), it's forwarded via UDP to the connected laptop
3. Messages appear instantly on your laptop through the desktop client

## Installation Guide

### Prerequisites

- Both devices must be on the same network

### Mobile Setup

1. Install the application on your Android device
2. Open the app and grant notification access when prompted
3. Fill the desktop IP address

### Desktop Setup

```bash
cd listener
node .
```

## Demo

<https://github.com/user-attachments/assets/419c064d-8d0a-4174-a72b-1d41d2c94be6>
