package com.michaelc.contacttracing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.michaelc.contacttracing.fragments.DataFragment
import com.michaelc.contacttracing.fragments.FormFragment
import kotlinx.android.synthetic.main.activity_display_data.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class DisplayData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_data)
        //====================================================================
        //**BOTTOM NAVBAR**
        try {

            val formFragment = FormFragment()
            val dataFragment = DataFragment()
            //makeCurrentFragment(formFragment)
            bottom_navigation.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.ic_form -> startActivity(Intent(this, ContactForm::class.java))
                    R.id.ic_data -> Log.d("Boom", "michael Chrystal 08/10/20")
                }
                true
            }
        } catch (e: Exception) {
            Log.d("ERR", e.toString())
        }

    }
}
