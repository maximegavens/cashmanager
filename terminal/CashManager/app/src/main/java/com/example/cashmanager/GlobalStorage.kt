package com.example.cashmanager

import android.app.Application

enum class connection {
    ESTABLISHED, REFUSED, IN_PROGRESS
}

class GlobalStorage: Application() {
    var state = connection.REFUSED.name
}