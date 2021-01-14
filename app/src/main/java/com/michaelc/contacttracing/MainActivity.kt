package com.michaelc.contacttracing

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
//    var formate = SimpleDateFormat("dd MMM, YYYY", Locale.US)

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


    fun getLastModified(directoryFilePath: String?): File? {
        val directory = File(directoryFilePath)
        val files = directory.listFiles { obj: File -> obj.isFile }
        var lastModifiedTime = Long.MIN_VALUE
        var chosenFile: File? = null
        if (files != null) {
            for (file in files) {
                if (file.lastModified() > lastModifiedTime) {
                    chosenFile = file
                    lastModifiedTime = file.lastModified()
                }
            }
        }
        return chosenFile
    }



    //=============================================================================

    //          on click of menu item
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

                //Save to device
        R.id.device_save -> {
            var csvString: String = ""
            csvString =
                getDBDataAndCreateCsvString()//get all the data in the db and place in a csv string

            val filepath = "MyFileStorage"
            // val path = this!!.filesDir.absolutePath // => /data/user/0/com.example.test/files

            //===========================FILE NAME==========================
            val sdf = SimpleDateFormat("dd-M-yyyy") //-hh:mm:ss" : not allowed in file name
            val currentDate = sdf.format(Date())
            val fileName = currentDate.toString() + ".csv"
            //==============================================================


            var myExternalFile: File = File(getExternalFilesDir(filepath), fileName)
            try {
                val fileOutPutStream = FileOutputStream(myExternalFile)
                fileOutPutStream.write(csvString.toByteArray())
                fileOutPutStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            Toast.makeText(
                this,
                "File saved to " + getFilesDir() + "/" + fileName,
                Toast.LENGTH_SHORT
            ).show()

            // User chose the "Settings" item, show the app settings UI...
            true
        }
        //Attach to email
        R.id.gmail_save -> {


            val filepath = "MyFileStorage"
            var absFilePath = getExternalFilesDir(filepath)

            var lastFile = getLastModified(absFilePath.toString())


            lateinit var email: String
            var subject: String = "Contact Tracing"
            // lateinit var message: String
            //  lateinit var uri: Uri

            //Create Email
            try {

                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:") // only email apps should handle this

                val root: File? = getExternalFilesDir(filepath)
                val pathToMyAttachedFile = lastFile.toString()
                val file = File(pathToMyAttachedFile)  //root, before path
                intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                val path = Uri.fromFile(file)
               // intent.putExtra(Uri.parse(path.toString()))// = Uri.parse("mailto:")
                intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));//add attachment
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }

            } catch (t: Throwable) {
                Toast.makeText(this, "Request failed try again: $t", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Log.d("email err", e.toString())
            }
            Toast.makeText(
                this,
                "File sent to Email App",
                Toast.LENGTH_SHORT
            ).show()
            // User chose the "Settings" item, show the app settings UI...
            true

        }
        //Save data to drive
        R.id.gdrive_save -> {
            Toast.makeText(
                this,
                "File saved to Google Drive",
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