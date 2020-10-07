package com.michaelc.contacttracing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.michaelc.contacttracing.fragments.DataFragment
import com.michaelc.contacttracing.fragments.FormFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val formFragment = FormFragment()
        val dataFragment = DataFragment()
try {

        makeCurrentFragment(formFragment)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_form -> startActivity(Intent(this, ContactForm::class.java))


                R.id.ic_data -> startActivity(Intent(this, ContactForm::class.java))
            }
            true
        }

        }
catch (e: Exception)
{
    val alertDialog = AlertDialog.Builder(this@MainActivity).create()
    alertDialog.setTitle("Alert")
    alertDialog.setMessage("Alert message to be shown"+ e.toString())
    alertDialog.setButton(
        AlertDialog.BUTTON_NEUTRAL, "OK"
    ) { dialog, which -> dialog.dismiss() }
    alertDialog.show()
}

        /*button.setOnClickListener {
            startActivity(Intent(this,ContactForm::class.java)
                  //  Log.d("joe""HERe")
            )


        }*/


    }
    private fun makeCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}