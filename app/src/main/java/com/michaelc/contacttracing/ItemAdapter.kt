package com.michaelc.contacttracing

import android.app.AlertDialog
import android.content.Context
import android.database.sqlite.SQLiteFullException
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils.getActivity
import com.michaelc.contacttracing.fragments.DataFragment
import kotlinx.android.synthetic.main.edit_dialog.view.*
import kotlinx.android.synthetic.main.fragment_data.*
import kotlinx.android.synthetic.main.item_row.view.*


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
        val nameTextView = rowView.findViewById(R.id.tvName) as TextView
// get the image views for edit and delete icons
        val editIcon = rowView.findViewById(R.id.ivEdit) as ImageView
        val deleteIcon = rowView.findViewById(R.id.ivDelete) as ImageView
      //  val confirmIcon = rowView.findViewById(R.id.mDialogView.dialogConfirmBtn)

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

            name = a.name
            number = a.number

            mDialogView.dialogNameEt.setText(name.toString())
            mDialogView.dialogNumberEt.setText(number.toString())

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
            /* Toast.makeText(
                 context,
                 "delete", Toast.LENGTH_SHORT
             ).show()*/

            var a = getItem(position) as ContactDetails
            var db = DatabaseHandler(context)
            db.deleteData(a)


          val gVData = MainActivity.GlobalVariable.mutableListData
            gVData.remove(a)
            notifyDataSetChanged()



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
