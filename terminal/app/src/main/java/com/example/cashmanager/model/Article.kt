package com.example.cashmanager.model

import java.util.*

class Article(articleId: Int): Observable() {

    var id: Int = articleId
        set(value) {
            field = value
            setChangedAndNotify("id")
        }

    var name: String = "None"
        set(value) {
            field = value
            setChangedAndNotify("name")
        }

    var price: String = "0"
        set(value) {
            field = value
            setChangedAndNotify("price")
        }

    private fun setChangedAndNotify(field: Any)
    {
        setChanged()
        notifyObservers(field)
    }
}