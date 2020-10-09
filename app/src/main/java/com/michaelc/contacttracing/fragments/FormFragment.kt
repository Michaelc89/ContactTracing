package com.michaelc.contacttracing.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.michaelc.contacttracing.ContactForm
import com.michaelc.contacttracing.MainActivity
import com.michaelc.contacttracing.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_form.*
import java.text.SimpleDateFormat
import java.util.*

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


            dateLabel.setText("HELP")
            //StartActivity(Intent(this, MainActivity::class.java))

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val textView = findViewById<TextView>(R.id.dateLabel)
        return inflater.inflate(R.layout.fragment_form, container, false)

    }


override fun onStart(){
    super.onStart()
    try {
        var formate = SimpleDateFormat("dd MMM, YYYY", Locale.US)

        //set the formats for current date and time
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.US)
        val simpleDateFormat = SimpleDateFormat("dd/MMM/YY")

        val currentDate: String = simpleDateFormat.format(Date())
        //set date to label
        dateLabel.setText(currentDate.toString())
        //get the time and then set it
        val  currentTime: String = timeFormat.format(Date().time)
        timeLabel.setText(currentTime.toString())


        //set the date when button is clicked
        dateLabel.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this.requireActivity(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, month)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val date2 = formate.format(selectedDate.time)//seleccted date to be put in class
                    dateLabel.setText(date2.toString())
                    //Log.d("Car",selectedDate.toString())
                    //Toast.makeText(this,"date : " + selectedDate,Toast.LENGTH_SHORT).show()
                },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }
        timeLabel.setOnClickListener {
            val now = Calendar.getInstance()
            var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)


            val timePicker = TimePickerDialog(this.requireActivity(), TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                timeLabel.text = timeFormat.format(selectedTime.time)
            },
                now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
            timePicker.show()

        }
    }
    catch(e: Exception){
        Log.d("LIVELINE",e.toString())
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