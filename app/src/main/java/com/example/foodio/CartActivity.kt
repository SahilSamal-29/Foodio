package com.example.foodio

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

class CartActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private var subTotal by Delegates.notNull<Double>()
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.rv) }
    private val seekBar by lazy { findViewById<SeekBar>(R.id.tipSeekBar) }
    private val tipPercentage by lazy { findViewById<TextView>(R.id.tvTipPercentage) }
    private val tvTipAmount by lazy { findViewById<TextView>(R.id.tvTipAmount) }
    private val tvTotalAmount by lazy { findViewById<TextView>(R.id.tvTotalAmount) }
    private val tvTipDescription by lazy { findViewById<TextView>(R.id.tvTipDescription) }
    private val btnPayment by lazy { findViewById<Button>(R.id.btnPayment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val tvSubTotal = findViewById<TextView>(R.id.subTotal)

        val cartItems: ArrayList<Items> = intent.extras?.getParcelableArrayList("cartItems")!!

        val cartAdapter = CartAdapter(cartItems, viewModel)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cartAdapter

        Log.d("CartActivity", "Selected items: $cartItems")

        subTotal = cartItems.sumOf { it.price }

        viewModel.cartItems.observe(this) { items ->
            Log.d("CartActivity", "Updated cart items: $cartItems")
            tvSubTotal.text = "- $${"%.2f".format(subTotal)}"
        }

        val initalTipPercentage = 5
        calculateTipAndTotal(subTotal, initalTipPercentage)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i("tag", "onProgressChanged $p1")
                tipPercentage.text = "${p1 * 5}%"
                calculateTipAndTotal(subTotal, p1 * 5)
                updateTipDescription(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })

        btnPayment.setOnClickListener {
            val dialog = ThankYouDialog()
            dialog.show(supportFragmentManager, "ThankYouDialog")
        }
    }

    private fun updateTipDescription(tipPerent: Int) {
        val tipDesccription = when (tipPerent) {
            in 1..3 -> "\uD83D\uDE0A"
            in 4..5 -> "\uD83D\uDE0D"
            6 -> "\uD83E\uDD29"
            else -> { " " }
        }
        tvTipDescription.text = tipDesccription
    }

    private fun calculateTipAndTotal(subTotal: Double, tipPercentage: Int) {
        val tipAmount = subTotal * tipPercentage / 100
        val totalAmount = subTotal + tipAmount

        tvTipAmount.text = "- $${"%.2f".format(tipAmount)}"
        tvTotalAmount.text = "- $${"%.2f".format(totalAmount)}"
        btnPayment.text = "Pay $${"%.2f".format(totalAmount)}"

    }
}
class ThankYouDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.thank_you_popup, null)

        val btnClose = view.findViewById<Button>(R.id.btnClosePopup)
        btnClose.setOnClickListener {
            dismiss()
        }

        builder.setView(view)
        return builder.create()
    }
}

