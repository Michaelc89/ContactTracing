package com.michaelc.contacttracing

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.database.sqlite.SQLiteFullException
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.edit_dialog.view.*
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*


class ItemAdapter(
    val context: Context,
    val dataSource: ArrayList<ContactDetails>
) :
    BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    //1
    override fun getCount(): Int {
        return dataSource.size
    }

    //2
    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // access the file item row to display the custom listview

        val rowView = inflater.inflate(R.layout.item_row, parent, false)
        // Get the name of an element on the page
        val nameTextView =  rowView.findViewById(R.id.tvName) as TextView
        // get the image views for edit and delete icons
        val editIcon =      rowView.findViewById(R.id.ivEdit) as ImageView
        val deleteIcon =    rowView.findViewById(R.id.ivDelete) as ImageView
        // val confirmIcon = rowView.findViewById(R.id.mDialogView.dialogConfirmBtn)

        //tried fab but not suitable
        /*try {
            val fab =           rowView.findViewById(R.id.fab) as? FloatingActionButton//floating action button

        }*/





        editIcon.setOnClickListener {
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.edit_dialog, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)
                .setTitle("Edit")

            var a = getItem(position) as ContactDetails

            var name: String
            var number: String
            var time: String
            var date: Date

            name = a.name
            number = a.number
            time = a.time
            date = a.date

            val pattern = "dd/MM/yyyy"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val dateString = simpleDateFormat.format(date)

            mDialogView.dialogNameEt.setText(name.toString())
            mDialogView.dialogNumberEt.setText(number.toString())
            mDialogView.dialogTimeEt.setText(time.toString())
            mDialogView.dialogDateEt.setText(dateString.toString())

            //show dialog
            val mAlertDialog = mBuilder.show()
            mDialogView.dialogConfirmBtn.setOnClickListener()
            {
                try {

                    val editedName: String
                    val editedNumber: String


                    editedName = mDialogView.dialogNameEt.text.toString()
                    editedNumber = mDialogView.dialogNumberEt.text.toString()


                    a.name = editedName
                    a.number = editedNumber



                    var db = DatabaseHandler(context)
                    db.updateData(a)
                    //dismiss dialog
                    //super.onBackPressed()
                    mAlertDialog.dismiss()


                } catch (sql: SQLiteFullException) {
                    Log.d("SQLLLLLL", sql.toString())
                }

            }


            //login button click of custom layout
            mDialogView.dialogCancelBtn.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
                //get text from EditTexts of custom layout
            }
        }

        deleteIcon.setOnClickListener {

            val mAlertDialog = AlertDialog.Builder(context)
            mAlertDialog.setIcon(R.mipmap.ic_launcher) //set alertdialog icon
            mAlertDialog.setTitle("DELETE!") //set alertdialog title
            mAlertDialog.setMessage("Are you sure you want to delete the contact") //set alertdialog message
            mAlertDialog.setPositiveButton("Yes") { dialog, id ->
                //perform some tasks here
                var a = getItem(position) as ContactDetails
                var db = DatabaseHandler(context)
                db.deleteData(a)


                val gVData = MainActivity.GlobalVariable.mutableListData
                gVData.remove(a)
                notifyDataSetChanged()
                Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show()
            }
            mAlertDialog.setNegativeButton("No") { dialog, id ->
                //perform som tasks here
                Toast.makeText(context, "No", Toast.LENGTH_SHORT).show()
            }
            mAlertDialog.show()



        }
        //set the data as a class ContactDetails
        val data = getItem(position) as ContactDetails
        //display the name
        nameTextView.setText(data.name.toString())

        return rowView
    }

    fun editRow(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.item_row, parent, false)
        val editIcon = rowView.findViewById(R.id.ivEdit) as ImageView

        /*editIcon.setOnClickListener() {
            Toast.makeText(
                context,
                "You clicked the edit button for id ",
                Toast.LENGTH_SHORT
            ).show()
        }*/


        return rowView
    }


    fun editData() {

        Toast.makeText(
            context,
            "You clicked the edit button for id ",
            Toast.LENGTH_SHORT
        ).show()
    }
}
