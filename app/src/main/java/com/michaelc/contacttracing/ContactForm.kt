package com.michaelc.contacttracing

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.michaelc.contacttracing.fragments.DataFragment
import com.michaelc.contacttracing.fragments.FormFragment
import kotlinx.android.synthetic.main.activity_contact_form.*
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import java.util.Date

class ContactForm : AppCompatActivity() {


    var formate = SimpleDateFormat("dd MMM, YYYY", Locale.US)
    //val cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_form)
        try {

        //set the formats for current date and time
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.US)
        val simpleDateFormat = SimpleDateFormat("dd/MMM/YY")

/*
*
*
* nav bar
*
*
*
*
* */

            /*val formFragment = FormFragment()
            val dataFragment = DataFragment()

            makeCurrentFragment(formFragment)
            bottom_navigation.setOnNavigationItemSelectedListener {
                when(it.itemId){
                R.id.ic_form -> makeCurrentFragment(formFragment)


                        R.id.ic_data -> makeCurrentFragment(dataFragment)
            }
                true

            }*/

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
                this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
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


                val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
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
                Log.d("ERR",e.toString())
        }


    }
    /*private fun makeCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper,fragment)
            commit()
        }*/
}
