package com.michaelc.contacttracing

import java.sql.Date
import java.sql.Time

class ContactDetails {


    var id: Int
    var name: String
    var number: Double
    var time: Time
    var date: Date


    constructor(id: Int,name: String,number: Double,time: Time,date: Date) {
        this.id = id
        this.name = name
        this.number = number
        this.time = time
        this.date = date
       }

}