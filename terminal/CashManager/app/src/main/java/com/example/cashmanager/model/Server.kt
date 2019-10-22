package com.example.cashmanager.model

import java.util.*

class Server(serverId: Int): Observable() {

    var id: Int = serverId
        set(value) {
            field = value
            setChangedAndNotify("id")
        }

    var password: String = "admin"
        set(value) {
            field = value
            setChangedAndNotify("password")
        }

    var status: String = "refused"
        set(value) {
            field = value
            setChangedAndNotify("status")
        }

    private fun setChangedAndNotify(field: Any)
    {
        setChanged()
        notifyObservers(field)
    }
}