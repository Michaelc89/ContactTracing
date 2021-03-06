package com.michaelc.contacttracing.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.michaelc.contacttracing.ContactDetails
import com.michaelc.contacttracing.DatabaseHandler
import com.michaelc.contacttracing.R
import kotlinx.android.synthetic.main.fragment_form.*
import kotlinx.android.synthetic.main.layout_form.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Number


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FormFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FormFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

            //dateLabel.setText("HELP")
           // TableNumber.setText("0")
            //StartActivity(Intent(this, MainActivity::class.java))

            //var db = DatabaseHandler(context)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val textView = findViewById<TextView>(R.id.dateLabel)
        return inflater.inflate(R.layout.layout_form, container, false)
       // setContentView(R.layout.layout_form);
        //cagnge xml file here


    }

    var calObj = Calendar.getInstance()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {

        //==========================================================================
        //             INITIATE THE DB
        val context: Context = this.context ?: return // or if block

        var db = DatabaseHandler(context)
        //var a = DatabaseHandler(this.context!!)

        //===========================================================================
        super.onStart()
        try {



            var formate = SimpleDateFormat("dd/MM/YYYY", Locale.US)

            //set the formats for current date and time
            val timeFormat = SimpleDateFormat("hh:mm a", Locale.US)
            val simpleDateFormat = SimpleDateFormat("dd/MM/YYYY")

            val currentDate: String = simpleDateFormat.format(Date())
            //set date to label
            dateLabel.setText(currentDate.toString())
            //get the time and then set it
            val currentTime: String = timeFormat.format(Date().time)
            timeLabel.setText(currentTime.toString())


            //set the date when button is clicked
            dateLabel.setOnClickListener {
                val now = Calendar.getInstance()
                val datePicker = DatePickerDialog(
                    this.requireActivity(),
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        val selectedDate = Calendar.getInstance()
                        selectedDate.set(Calendar.YEAR, year)
                        selectedDate.set(Calendar.MONTH, month)
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        val date2 =
                            formate.format(selectedDate.time)//selected date to be put in class
                        dateLabel.setText(date2.toString())

                    },
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
                )
                datePicker.show()
            }
            timeLabel.setOnClickListener {
                val now = Calendar.getInstance()
                var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)
                val simpleDateFormat = SimpleDateFormat("dd/MM/YYYY")

                val timePicker = TimePickerDialog(
                    this.requireActivity(),
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        val selectedTime = Calendar.getInstance()
                        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        selectedTime.set(Calendar.MINUTE, minute)
                       // timeLabel.text = timeFormat.format(selectedTime.time)

                        calObj.time = selectedTime.time
                    },
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    false
                )
                timePicker.show()


            }
        } catch (e: Exception) {
            Log.d("LIVELINE", e.toString())
        }

        fun empty(txvn: TextView) {
            val dialogBuilder = AlertDialog.Builder(activity!!)
            dialogBuilder.setTitle("Warning")
            dialogBuilder.setMessage("All fields must be filled in!!\n" + txvn.hint.toString() + " Is empty")
            dialogBuilder.show()
        }

        buttonCreate.setOnClickListener {

            //formats
            val df: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            //inputted date
            val dateInput = dateLabel.text.toString()
            //convert string to date
            val convertedDate = df.parse(dateInput)
            // formatted date
            val formattedDate = df.format(convertedDate)

            Log.d("convertedDate", convertedDate.toString())
            Log.d("convertedDate", formattedDate.toString())
            Log.d("convertedDate type", convertedDate.javaClass.name.toString())



            try {
                if (Name.text.isEmpty()) {

                    empty(Name)

                } else if (Number.text.isEmpty()) {
                    empty(Number)
                } else {
                    //set inputs to class
                    val cont = ContactDetails(
                        1,
                        Name.text.toString(),
                        Number.text.toString(),
                        TableNumber.text.toString(),
                        timeLabel.text.toString(),
                        //dateLabel.text.toString()
                        convertedDate
                    )

                    db.insertData(cont)

                    Name.text=null
                    Number.text=null
                    TableNumber.text=null

                }
            } catch (e: Exception) {
                Log.d("Darcy", e.toString())
            }
        }

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FormFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FormFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }


    }
}