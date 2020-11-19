package com.michaelc.contacttracing

import android.R.attr.data
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.michaelc.contacttracing.fragments.DataFragment
import com.michaelc.contacttracing.fragments.FormFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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
        inflater.inflate(R.menu.action_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //=============================================================================
    //                  LOADS ALL THE DATA IN THE DB INTO A STRING
fun getDBDataAndCreateCsvString(): String {
        //==========================================================================
        //             INITIATE THE DB

        var db = DatabaseHandler(this)

        var csvData=""
        //===========================================================================
        var data = db.readData()
        if (  data.size ==0)
        {
            Toast.makeText(
                this,
                "There is no data in the Database to export ",
                Toast.LENGTH_SHORT
            ).show()

        }
        else
        {
            var HEADERS: String = "NAME,NUMBER,TABLE NUMBER,TIME,DATE\n"
            var str = StringBuilder(HEADERS)


            for (item in data) {

                str.append(item.name.toString()).append(",").append(item.number.toString()).append(",").append(
                    item.tableNumber.toString()
                ).append(",").append(item.time.toString()).append(",").append(item.date.toString()).append(
                    "\n"
                )

            }
            csvData = str.toString()
        }
            return csvData

    }




    //=============================================================================

    //          on click of menu item
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        //Save to device
        R.id.device_save -> {
            var csvString: String = ""
            csvString= getDBDataAndCreateCsvString()//get all the data in the db and place in a csv string


            // val path = this!!.filesDir.absolutePath // => /data/user/0/com.example.test/files

//===========================FILE NAME==========================
            val sdf = SimpleDateFormat("dd/M/yyyy-hh:mm:ss")
            val currentDate = sdf.format(Date())
            val fileName = currentDate.toString() + ".csv"
//==============================================================

            File(fileName).printWriter().use { out ->
                out.println(csvString)
                //out.println(outStr2)
            }





            /*Toast.makeText(
                this,
                "Saved to "+ getFilesDir()+"/"+fileName ,
                Toast.LENGTH_SHORT
            ).show()*/





            Toast.makeText(
                this,
                "You clicked the edit button for id ",
                Toast.LENGTH_SHORT
            ).show()
            // User chose the "Settings" item, show the app settings UI...
            true
        }
        //Attach to email
        R.id.gmail_save -> {
            Toast.makeText(
                this,
                "You clicked the Gmail button for id ",
                Toast.LENGTH_SHORT
            ).show()
            // User chose the "Settings" item, show the app settings UI...
            true
        }
        //Save data to drive
        R.id.gdrive_save -> {
            Toast.makeText(
                this,
                "You clicked the drive button for id ",
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