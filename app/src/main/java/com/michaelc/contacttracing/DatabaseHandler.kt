package com.michaelc.contacttracing

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_form.*
import java.lang.Exception
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

val DATABASE_NAME = "MYDB"
val TABLE_NAME = "Contact"
val COL_NAME = "name"
val COL_NUMBER = "number"
val COL_TABLENUMBER = "tablenumber"
val COL_TIME = "time"
val COL_DATE = "date"
val COL_ID = "id"

class DatabaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " VARCHAR(256)," +
                COL_NUMBER + " VARCHAR(256)," +
                COL_TABLENUMBER + " VARCHAR(256)," +
                COL_DATE + " VARCHAR(256)," +
                COL_TIME + " VARCHAR(256));"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // TODO("Not yet implemented")

    }
    //===========================================================
    // INSERT DATA FUNCTION
    //===========================================================

    fun insertData(cd: ContactDetails) {
        val db = this.writableDatabase
        var CV = ContentValues()
        CV.put(COL_NAME, cd.name)
        CV.put(COL_NUMBER, cd.number)
        CV.put(COL_TABLENUMBER,cd.tableNumber)
        CV.put(COL_DATE, cd.date.toString())
        CV.put(COL_TIME, cd.time)
        var result = db.insert(TABLE_NAME, null, CV)
        //https://www.youtube.com/watch?v=OxHNcCXnxnE   5:03
        if (result == -1.toLong()) {
            Toast.makeText(context, "INSERT FAILED", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    //===========================================================
    // READ DATA FUNCTION
    //===========================================================

    fun readData(): MutableList<ContactDetails> {
        var list: MutableList<ContactDetails> = ArrayList()


        val cal: Calendar = Calendar.getInstance()

        val dateHolder: Date = Date(110570)

        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var contD = ContactDetails(1, "1", "1","1", "1", dateHolder)
                contD.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                contD.name = result.getString(result.getColumnIndex(COL_NAME))
                contD.number = result.getString(result.getColumnIndex(COL_NUMBER))
                contD.tableNumber = result.getString(result.getColumnIndex(COL_TABLENUMBER))
                contD.time = result.getString(result.getColumnIndex(COL_TIME))
               // contD.date= result.getString(result.getColumnIndex(COL_DATE))

                val date = result.getString(result.getColumnIndex(COL_DATE))//string

                //formats date in way the date obj is formatted
                val df: DateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)

                //convert string to date
                val convertedDate = df.parse(date)

                contD.date =  convertedDate

                list.add(contD)
            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }
    //===========================================================
    // UPDATE DATA FUNCTION
    //===========================================================

    fun updateData(ct: ContactDetails) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, ct.name)
        cv.put(COL_NUMBER, ct.number)
        cv.put(COL_TABLENUMBER,ct.tableNumber)

        var result = db.update(TABLE_NAME, cv, " id = " + ct.id, null)

        if (result == -1) {
            Toast.makeText(context, "EDIT FAILED", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }

        db.close()
    }

    //===========================================================
    // DELETE DATA FUNCTION
    //===========================================================

    fun deleteData(ct: ContactDetails) {
        val db = this.writableDatabase
        val cv = ContentValues()


        var result = db.delete(TABLE_NAME, " id = " + ct.id, null)

        if (result == -1) {
            Toast.makeText(context, "EDIT FAILED", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }

        db.close()
    }


}

