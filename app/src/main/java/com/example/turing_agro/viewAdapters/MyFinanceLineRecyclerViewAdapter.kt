package com.example.turing_agro.viewAdapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.turing_agro.databinding.FinanceLineBinding
import com.example.turing_agro.models.Finance
import java.text.NumberFormat
import java.util.Currency

class MyFinanceLineRecyclerViewAdapter(
    private val values: ArrayList<Finance>
) : RecyclerView.Adapter<MyFinanceLineRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FinanceLineBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.itemText.text = item.nome
        holder.quantityText.text = item.quantidade.toString()
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        format.currency = Currency.getInstance("BRL")

        holder.expectedText.text = format.format(item.previsto)

        holder.confirmedText.setText(format.format(item.real))



    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FinanceLineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val itemText: TextView = binding.lineItem
        val quantityText: TextView = binding.lineQuantity
        val expectedText: TextView = binding.lineExpectedValue
        val confirmedText: EditText = binding.lineConfirmedValue

        override fun toString(): String {
            return super.toString() + " '" + itemText.text + "'"
        }
    }

    class EditTextWatcher(data: Finance) : TextWatcher {
        private val value: Finance
        init {
            this.value = data
        }
        override fun afterTextChanged(s: Editable) {
            value.real = s.toString().toFloat()
        }
        override fun beforeTextChanged(
            arg0: CharSequence, arg1: Int, arg2: Int,
            arg3: Int
        ) {
        }
        override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {

        }
    }

}