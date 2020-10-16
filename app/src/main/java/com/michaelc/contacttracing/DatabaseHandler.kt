package com.michaelc.contacttracing

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "MYDB"
val TABLE_NAME = "Contact"
val COL_NAME="name"
val COL_NUMBER="number"
val COL_TIME="time"
val COL_DATE="date"
val COL_ID="id"

class DatabaseHandler(var context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME + " ("+
                COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COL_NAME+" VARCHAR(256),"+
                COL_NUMBER+" VARCHAR(256),"+
                COL_DATE+" VARCHAR(256),"+
                COL_TIME+" VARCHAR(256));"

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
        CV.put(COL_DATE, cd.date)
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

        fun readData() : MutableList<ContactDetails>{
            var list : MutableList<ContactDetails> = ArrayList()

            val db = this.readableDatabase
            val query = "Select * from " + TABLE_NAME
            val result = db.rawQuery(query,null)
            if(result.moveToFirst()){
                do {
                    var contD = ContactDetails(1,"1","1","1","1")
                    contD.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                    contD.name = result.getString(result.getColumnIndex(COL_NAME))
                    contD.time = result.getString(result.getColumnIndex(COL_TIME))
                    contD.date = result.getString(result.getColumnIndex(COL_DATE))
                    list.add(contD)
                }while (result.moveToNext())
            }

            result.close()
            db.close()
            return list
        }
        //===========================================================
        // UPDATE DATA FUNCTION
        //===========================================================

//        fun updateData() {
//            val db = this.writableDatabase
//            val query = "Select * from " + TABLE_NAME
//            val result = db.rawQuery(query,null)
//            if(result.moveToFirst()){
//                do {
//                    var cv = ContentValues()
//                    cv.put(COL_AGE,(result.getInt(result.getColumnIndex(COL_AGE))+1))
//                    db.update(TABLE_NAME,cv,COL_ID + "=? AND " + COL_NAME + "=?",
//                        arrayOf(result.getString(result.getColumnIndex(COL_ID)),
//                            result.getString(result.getColumnIndex(COL_NAME))))
//                }while (result.moveToNext())
//            }
//
//            result.close()
//            db.close()
//        }

        //===========================================================
        // DELETE DATA FUNCTION
        //===========================================================

        fun deleteData(){
            val db = this.writableDatabase
            db.delete(TABLE_NAME,null,null)
            db.close()
        }


    }

