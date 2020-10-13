package com.michaelc.contacttracing

import java.sql.Date
import java.sql.Time

class ContactDetails {


    var id: Int
    var name: String
    var number: String
    var time: String
    var date: String


    constructor(id: Int,name: String,number: String,time: String,date: String) {
        this.id = id
        this.name = name
        this.number = number
        this.time = time
        this.date = date
       }

}