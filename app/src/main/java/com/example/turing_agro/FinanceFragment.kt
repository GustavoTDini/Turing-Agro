package com.example.turing_agro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.turing_agro.models.Finance
import com.example.turing_agro.viewAdapters.MyFinanceLineRecyclerViewAdapter
import java.text.NumberFormat
import java.util.Currency


class FinanceFragment : Fragment() {

    lateinit var outcomeList: ArrayList<Finance>
    lateinit var incomeList: ArrayList<Finance>
    var expectedTotalIncome = 0f
    var expectedTotalOutcome = 0f
    var confirmedTotalIncome = 0f
    var confirmedTotalOutcome = 0f
    var expectedBalance = 0f
    var confirmedBalance = 0f


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

        for (finance in incomeList){
            expectedTotalIncome += finance.previsto
            confirmedTotalIncome += finance.real
        }

        for (finance in outcomeList){
            expectedTotalOutcome += finance.previsto
            confirmedTotalOutcome += finance.real
        }

        expectedBalance = expectedTotalIncome - expectedTotalOutcome
        confirmedBalance = confirmedTotalIncome - confirmedTotalOutcome

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

        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        format.currency = Currency.getInstance("BRL")

        val expectedTotalIncomeView = view.findViewById<TextView>(R.id.expected_total_income)
        expectedTotalIncomeView.text = format.format(expectedTotalIncome)
        val confirmedTotalIncomeView = view.findViewById<TextView>(R.id.confirmed_total_income)
        confirmedTotalIncomeView.text = format.format(confirmedTotalIncome)

        val expectedTotalOutcomeView = view.findViewById<TextView>(R.id.expected_total_outcome)
        expectedTotalOutcomeView.text = format.format(expectedTotalOutcome)
        val confirmedTotalOutcomeView = view.findViewById<TextView>(R.id.confirmed_total_outcome)
        confirmedTotalOutcomeView.text = format.format(confirmedTotalOutcome)

        val expectedBalanceView = view.findViewById<TextView>(R.id.expected_balance)
        expectedBalanceView.text = format.format(expectedBalance)
        val confirmedBalanceView = view.findViewById<TextView>(R.id.confirmed_balance)
        confirmedBalanceView.text = format.format(confirmedBalance)

        return view
    }

}