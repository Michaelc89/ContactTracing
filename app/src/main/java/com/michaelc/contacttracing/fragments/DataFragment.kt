package com.michaelc.contacttracing.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.michaelc.contacttracing.*
//import com.michaelc.contacttracing.MainActivity.GlobalVariable.adapter
import com.michaelc.contacttracing.MainActivity.GlobalVariable.mutableListData
import kotlinx.android.synthetic.main.fragment_data.*
import kotlinx.android.synthetic.main.item_row.*


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
        val context: Context = this.context ?: return // or if block

    }

   /* object GlobalVariable {
        var mutableListData = ArrayList<ContactDetails>()
    }*/

    fun loadDB() {
        try {
            //==========================================================================
            //             INITIATE THE DB
            val context: Context = this.context ?: return // or if block

            var db = DatabaseHandler(context)
            //===========================================================================
            var data = db.readData()


             MainActivity.GlobalVariable.mutableListData = ArrayList<ContactDetails>()
            //val contact = mutableListData.iterator()

            for (item in data) {
                MainActivity.GlobalVariable.mutableListData.add(item)
                Log.d("Gary", item.toString())
            }

            //====================================================
            //EDIT DATA
            //====================================================
            //adapter = null
            var adapter =  ItemAdapter(context, mutableListData)
            listView.adapter = adapter




            var positions: Int
            positions = data.size


        } catch (e: Exception) {
            Log.d("Simon", e.toString())
            Log.d("Simon2", e.cause.toString())
            Log.d("simon3", e.message.toString())
        }

    }


    /*fun doEditData(view: View)
    {
        doEditData(view)
        Toast.makeText(
            context,
            "You clicked the edit button for id " ,
            Toast.LENGTH_SHORT
        ).show()
    }*/


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