package com.example.turing_agro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.turing_agro.models.Supply
import com.example.turing_agro.viewAdapters.MySupplyRecyclerViewAdapter


/**
 * A fragment representing a list of Items.
 */
class SupplyFragment : Fragment() {

    lateinit var list: ArrayList<Supply>
    override fun onCreate(args: Bundle?) {
        super.onCreate(args)

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
        list = bundle.getSerializable("suppliers") as ArrayList<Supply>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        args: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_supply_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                adapter = MySupplyRecyclerViewAdapter(list)
            }
        }

        return view
    }
}