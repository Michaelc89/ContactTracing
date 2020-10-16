package com.michaelc.contacttracing.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.michaelc.contacttracing.ContactForm
import com.michaelc.contacttracing.DatabaseHandler
import com.michaelc.contacttracing.R
import kotlinx.android.synthetic.main.fragment_data.*
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DataFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)



           // fragment_data
            /*val intent = Intent(activity, ContactForm::class.java)
            startActivity(intent)*/
           // StartActivity(Intent(this, ContactForm::class.java))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_data, container, false)


    }

    override fun onStart() {

        loadDB()
        super.onStart()

    }

    fun loadDB(){
        try {


            //==========================================================================
            //             INITIATE THE DB
            val context: Context = this.context ?: return // or if block

            var db = DatabaseHandler(context)
            //var a = DatabaseHandler(this.context!!)


            //===========================================================================

            var data = db.readData()
            tvName.text = ""
            for (i in 0..(data.size - 1)) {
                tvName.append(data.get(i).name + "\n")
                //            tvName.append(data.get(i).id.toString() + " " + data.get(i).name + " " + data.get(i).number + "\n")
            }
        }
        catch (e:Exception)
        {
            Log.d("A MAN", e.toString())
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DataFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DataFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}