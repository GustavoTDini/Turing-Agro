package com.example.turing_agro.viewAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.turing_agro.databinding.FragmentFoodBinding
import com.example.turing_agro.models.Food


class MyFoodRecyclerViewAdapter(
    private val values: ArrayList<Food>
) : RecyclerView.Adapter<MyFoodRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.itemName.text = item.alimento
        "${item.venda}%".also { holder.sellPercent.text = it }
        "${item.consumo}%".also { holder.selfPercent.text = it }
        holder.itemImage.setImageResource(item.imagem)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val itemName: TextView = binding.foodName
        val itemImage: ImageView = binding.foodItemImage
        val selfPercent: TextView = binding.selfPercentage
        val sellPercent: TextView = binding.sellPercentage

        override fun toString(): String {
            return super.toString() + " '" + itemName.text + "'"
        }
    }
}