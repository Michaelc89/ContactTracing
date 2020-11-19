package com.michaelc.contacttracing


import java.text.SimpleDateFormat
import java.util.*

class ContactDetails {


    var id: Int
    var name: String
    var number: String
    var tableNumber: String?
    var time: String
    var date: Date


    constructor(id: Int,name: String,number: String,tableNumber: String?,time: String,date: Date) {
        this.id = id
        this.name = name
        this.number = number
        this.tableNumber = tableNumber
        this.time = time
        this.date = date
       }

}