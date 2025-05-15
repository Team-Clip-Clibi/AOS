package com.sungil.billing

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.sungil.billing.databinding.ActivityBlillingBinding
import com.tosspayments.paymentsdk.PaymentWidget
import com.tosspayments.paymentsdk.model.PaymentCallback
import com.tosspayments.paymentsdk.model.PaymentWidgetStatusListener
import com.tosspayments.paymentsdk.model.TossPaymentResult
import com.tosspayments.paymentsdk.view.PaymentMethod

class BillingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBlillingBinding
    private lateinit var paymentWidget : PaymentWidget
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlillingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.billing_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
        addListener()
    }

    private fun init() {
        paymentWidget = PaymentWidget(
            activity = this@BillingActivity,
            clientKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm",
            customerKey = "JuNm6zJ0Afr7xUwStZAwn"
        )
        val paymentMethodWidgetStatusListener = object : PaymentWidgetStatusListener {
            override fun onFail(fail: TossPaymentResult.Fail) {
                Log.d(
                    javaClass.name.toString(),
                    "Error : ${fail.errorCode} , message : ${fail.errorMessage}"
                )
                finish()
            }

            override fun onLoad() {
                Log.d(javaClass.name.toString(), "success to get Loading")
            }
        }
        paymentWidget.run {
            renderPaymentMethods(
                method = binding.paymentWidget,
                amount = PaymentMethod.Rendering.Amount(10000),
                paymentWidgetStatusListener = paymentMethodWidgetStatusListener
            )
            renderAgreement(binding.agreementWidget)
        }
    }

    private fun addListener(){
        binding.payButton.setOnClickListener {
            paymentWidget.requestPayment(
                paymentInfo = PaymentMethod.PaymentInfo(orderId = "test" , orderName = "oneThing"),
                paymentCallback = object : PaymentCallback{
                    override fun onPaymentFailed(fail: TossPaymentResult.Fail) {
                        Log.e(javaClass.name.toString(),"errorCode : ${fail.errorCode} , message : ${fail.errorMessage}")
                    }

                    override fun onPaymentSuccess(success: TossPaymentResult.Success) {
                        Log.i(javaClass.name.toString(), success.paymentKey)
                        Log.i(javaClass.name.toString(), success.orderId)
                        Log.i(javaClass.name.toString(), success.amount.toString())
                    }
                }
            )
        }
    }

}