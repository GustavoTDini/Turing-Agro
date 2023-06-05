package com.example.turing_agro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.turing_agro.models.Finance
import com.example.turing_agro.viewAdapters.MyFinanceLineRecyclerViewAdapter

class FinanceFragment : Fragment() {

    lateinit var outcomeList: ArrayList<Finance>
    lateinit var incomeList: ArrayList<Finance>

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
        incomeList = bundle.getSerializable("income") as ArrayList<Finance>

        outcomeList = bundle.getSerializable("outcome") as ArrayList<Finance>

        Log.d("DEBUG_TESTE", incomeList.toString())
        Log.d("DEBUG_TESTE", outcomeList.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_finance, container, false)

        val outcomeListView = view.findViewById<RecyclerView>(R.id.outcome_list)
        with(outcomeListView){
            adapter =  MyFinanceLineRecyclerViewAdapter(outcomeList)
        }

        val incomeListView = view.findViewById<RecyclerView>(R.id.income_list)
        with(incomeListView){
            adapter =  MyFinanceLineRecyclerViewAdapter(incomeList)
        }
        return view
    }
}