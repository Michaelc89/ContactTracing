package com.michaelc.contacttracing

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.michaelc.contacttracing.fragments.DataFragment
import com.michaelc.contacttracing.fragments.FormFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_form.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    var formate = SimpleDateFormat("dd MMM, YYYY", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setSupportActionBar(findViewById(R.id))




//====================================================================
        //**BOTTOM NAVBAR**
        val formFragment = FormFragment()
        val dataFragment = DataFragment()
        makeCurrentFragment(formFragment)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_form -> makeCurrentFragment(formFragment)
                R.id.ic_data -> makeCurrentFragment(dataFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

    object GlobalVariable {

        var mutableListData = ArrayList<ContactDetails>()
        //var adapter =  ItemAdapter(context, mutableListData)
    }


    //=============================================================================
//                      MENU BAR

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.action_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    //          on click of menu item
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        //Save to device
        R.id.device_save -> {
            Toast.makeText(
                this,
                "You clicked the edit button for id " ,
                Toast.LENGTH_SHORT
            ).show()
            // User chose the "Settings" item, show the app settings UI...
            true
        }
        //Attach to email
        R.id.gmail_save -> {
            Toast.makeText(
                this,
                "You clicked the Gmail button for id " ,
                Toast.LENGTH_SHORT
            ).show()
            // User chose the "Settings" item, show the app settings UI...
            true
        }
        //Save data to drive
        R.id.gdrive_save -> {
            Toast.makeText(
                this,
                "You clicked the drive button for id " ,
                Toast.LENGTH_SHORT
            ).show()
            // User chose the "Settings" item, show the app settings UI...
            true
        }
        //add ICON to app bar
        /*R.id.action_favorite -> {
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
            true
        }*/

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}