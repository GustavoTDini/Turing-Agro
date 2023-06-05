package com.example.turing_agro.viewAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.turing_agro.databinding.FragmentSupplyBinding
import com.example.turing_agro.models.Supply


class MySupplyRecyclerViewAdapter(
    private val values: ArrayList<Supply>
) : RecyclerView.Adapter<MySupplyRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentSupplyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.itemText.text = item.insumo
        holder.quantityText.text = item.quantidade.toString()
        holder.cropText.text = item.area
        holder.supplierText.text = item.fornecedor
        holder.imageView.setImageResource(item.imagem)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentSupplyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val itemText: TextView = binding.supplyItem
        val cropText: TextView = binding.supplyCrop
        val quantityText: TextView = binding.supplyQuantity
        val supplierText: TextView = binding.supplySupplier
        val imageView: ImageView = binding.supplyItemImage

        override fun toString(): String {
            return super.toString() + " '" + itemText.text + "'"
        }
    }
}