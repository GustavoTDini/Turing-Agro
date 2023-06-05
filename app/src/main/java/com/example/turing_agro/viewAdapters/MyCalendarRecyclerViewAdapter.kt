package com.example.turing_agro.viewAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.turing_agro.databinding.FragmentDateBinding
import com.example.turing_agro.models.Calendar


class MyCalendarRecyclerViewAdapter(
    private val values: ArrayList<Calendar>
) : RecyclerView.Adapter<MyCalendarRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.itemText.text = item.data
        holder.areaText.text = item.area
        holder.actionText.text = item.acao
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val itemText: TextView = binding.date
        val actionText: TextView = binding.dateCrop
        val areaText: TextView = binding.dateAction

        override fun toString(): String {
            return super.toString() + " '" + itemText.text + "'"
        }
    }
}