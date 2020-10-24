package com.michaelc.contacttracing

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.edit_dialog.view.*
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

        editIcon.setOnClickListener {


            var a = getItem(position) as ContactDetails

            var name: String
            var number: String

            name = a.name
            number = a.number



            //Inflate the dialog with custom view
                val mDialogView = LayoutInflater.from(context).inflate(R.layout.edit_dialog, null)
                //AlertDialogBuilder
                val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogView)
                    .setTitle("Edit")

            mDialogView.dialogNameEt.setText(name.toString())
            mDialogView.dialogNumberEt.setText(number.toString())


            /*name =   mDialogView.dialogNameEt.text.toString()
            number =   mDialogView.dialogNumberEt.text.toString()*/
                //show dialog
                val  mAlertDialog = mBuilder.show()
                //login button click of custom layout
                mDialogView.dialogCancelBtn.setOnClickListener {
                    //dismiss dialog
                    mAlertDialog.dismiss()
                    //get text from EditTexts of custom layout





                }
                //cancel button click of custom layout
                mDialogView.dialogCancelBtn.setOnClickListener {
                    //dismiss dialog
                    mAlertDialog.dismiss()
                }
         //   }
        }

        deleteIcon.setOnClickListener {
            Toast.makeText(
                context,
                "delete", Toast.LENGTH_SHORT
            ).show()


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

        editIcon.setOnClickListener() {
            Toast.makeText(
                context,
                "You clicked the edit button for id ",
                Toast.LENGTH_SHORT
            ).show()
        }


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
