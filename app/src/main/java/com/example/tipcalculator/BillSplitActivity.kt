package com.example.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bill_split.*

class BillSplitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_split)


        val baseAmount = intent.getIntExtra("principal_amount", 0)

        amountTV.text = baseAmount.toString()

        calculateBtn.setOnClickListener {
            try {
                val memberNumber = noOfMembersET.text.toString().toInt()
                val splitAmount = Math.floor((baseAmount/memberNumber).toDouble())
                billsplittedTV.text = splitAmount.toString() + " per person"
            } catch (e: Exception) {
                Toast.makeText(this, "Enter no of people !", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
