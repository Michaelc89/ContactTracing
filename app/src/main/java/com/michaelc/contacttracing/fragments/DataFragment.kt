package com.michaelc.contacttracing.fragments

//import com.michaelc.contacttracing.MainActivity.GlobalVariable.adapter
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.michaelc.contacttracing.*
import com.michaelc.contacttracing.MainActivity.GlobalVariable.mutableListData
import kotlinx.android.synthetic.main.fragment_data.*


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
        /*val rowView = inflater.inflate(R.layout.item_row, container, false)
        val fab = rowView.findViewById(R.id.fab) as FloatingActionButton//floating action button
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }*/

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        loadDB()
        super.onStart()
        val context: Context = this.context ?: return // or if block



    }



    @RequiresApi(Build.VERSION_CODES.O)
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
                //val date = SimpleDateFormat("dd-MM-yyyy").parse(item.date)
                //mutableListData.groupBy { x-> date }
                MainActivity.GlobalVariable.mutableListData.add(item)
                Log.d("Gary", item.toString())
                mutableListData.sortBy { it.date }
            }

            //adapter = null
            var adapter =  ItemAdapter(context, mutableListData)
            listView.adapter = adapter


        } catch (e: Exception) {
            Log.d("Simon", e.toString())
            Log.d("Simon2", e.cause.toString())
            Log.d("simon3", e.message.toString())
            Log.d("simon3", e.cause.toString())
            Log.d("simon3", e.localizedMessage.toString())
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