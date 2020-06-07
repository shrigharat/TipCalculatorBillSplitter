package com.example.tipcalculator

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val INITIAL_TIP_PERCENT = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBarTip.progress = INITIAL_TIP_PERCENT
        tvTipPercentage.text = "$INITIAL_TIP_PERCENT%"
        seekBarTip.setOnSeekBarChangeListener( object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvTipPercentage.text = "$progress%"
                computeTipAndTotal()
                feedback(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        etBase.addTextChangedListener(object: TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                computeTipAndTotal()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        val actionBar = actionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#101211"))
        actionBar?.setBackgroundDrawable(colorDrawable)

        billsplitBTn.setOnClickListener {
            try {
                val intent = Intent(this, BillSplitActivity::class.java)
                intent.putExtra("principal_amount", etBase.text.toString().toInt())
                startActivity(intent)
            } catch (e:Exception) {
                Toast.makeText(this, "Enter some base amount !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun computeTipAndTotal()  {
        if(etBase.text.toString().isEmpty()) {
            tvtipshow.text = "Enter amount"
            tvTotalShow.text = "Enter amount"
            return
        }

        val baseAmount = etBase.text.toString().toDouble()
        val tipPercentage = seekBarTip.progress
        val tipAmount = tipPercentage * baseAmount / 100
        tvtipshow.text = "%.2f".format(tipAmount)
        tvTotalShow.text = "%.2f".format((tipAmount + baseAmount))
    }

    fun feedback(tipPercentage : Int) {
        when {
            tipPercentage in 0..5 -> {
                tvFeedback.text = "Poor"
            }
            tipPercentage in 6..15-> {
                tvFeedback.text = "Good"
            }
            tipPercentage in 16..30 -> {
                tvFeedback.text = "Excellent"
            }
        }
    }
}
