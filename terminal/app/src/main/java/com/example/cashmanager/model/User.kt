package com.example.cashmanager.model

import java.util.Observable

class User(userId: Int): Observable() {

    var id: Int = userId
        set(value) {
            field = value
            setChangedAndNotify("id")
        }

    var login: String = ""
    set(value) {
        field = value
        setChangedAndNotify("login")
    }

    var password: String = ""
        set(value) {
            field = value
            setChangedAndNotify("password")
        }

    var username: String = ""
        set(value) {
            field = value
            setChangedAndNotify("username")
        }

    private fun setChangedAndNotify(field: Any)
    {
        setChanged()
        notifyObservers(field)
    }
}