package com.example.turing_agro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.turing_agro.models.Calendar
import com.example.turing_agro.viewAdapters.MyCalendarRecyclerViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A fragment representing a list of Items.
 */
class CalendarFragment : Fragment() {

    lateinit var list: ArrayList<Calendar>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = requireActivity().intent
        if (intent == null) {
            Log.v("LOG_TAG", "Intent is null")
            return
        }
        val bundle = intent.extras
        if (bundle == null) {
            Log.v("LOG_TAG", "bundle is: NULL")
            return
        }
        list = bundle.getSerializable("calendar") as ArrayList<Calendar>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_date_list, container, false)

        val addButton = view.findViewById<FloatingActionButton>(R.id.add_calendar_button)
        addButton.setOnClickListener{
            Toast.makeText(requireContext(), "Adicionando datas para Agenda Google", Toast.LENGTH_LONG).show()
            }

        val listView = view.findViewById<RecyclerView>(R.id.list)
        with(listView){
            adapter =  MyCalendarRecyclerViewAdapter(list)
        }
        return view
    }
}