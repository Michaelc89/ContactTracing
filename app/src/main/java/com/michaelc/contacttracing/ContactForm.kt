package com.michaelc.contacttracing

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import androidx.fragment.app.Fragment
import com.michaelc.contacttracing.fragments.DataFragment
import com.michaelc.contacttracing.fragments.FormFragment
import kotlinx.android.synthetic.main.activity_contact_form.*
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import java.util.Date

class ContactForm : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_form)

        //====================================================================
        //**BOTTOM NAVBAR**
        val formFragment = FormFragment()
        val dataFragment = DataFragment()
        //makeCurrentFragment(formFragment)
        bottom_navigation2.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_form -> Log.d("form","hi")


                R.id.ic_data -> startActivity(Intent(this, DisplayData::class.java))
            }
            true
        }


    }

}
