package com.example.turing_agro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.turing_agro.models.Food
import com.example.turing_agro.viewAdapters.MyFoodRecyclerViewAdapter

/**
 * A fragment representing a list of Items.
 */
class FoodFragment : Fragment() {

    lateinit var list: ArrayList<Food>
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
        list = bundle.getSerializable("food") as ArrayList<Food>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_food_list, container, false)
        if (view is RecyclerView) {
            with(view) {
                adapter = MyFoodRecyclerViewAdapter(list)
            }
        }
        return view
    }

}