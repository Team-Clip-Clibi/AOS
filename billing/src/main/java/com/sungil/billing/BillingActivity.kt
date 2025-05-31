package com.sungil.billing

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sungil.billing.databinding.ActivityBlillingBinding
import com.sungil.domain.model.Router
import com.tosspayments.paymentsdk.PaymentWidget
import com.tosspayments.paymentsdk.model.PaymentCallback
import com.tosspayments.paymentsdk.model.PaymentWidgetStatusListener
import com.tosspayments.paymentsdk.model.TossPaymentResult
import com.tosspayments.paymentsdk.view.PaymentMethod
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BillingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBlillingBinding
    private lateinit var paymentWidget: PaymentWidget
    private lateinit var orderId: String
    private lateinit var userId: String
    private lateinit var matchInfo: String
    private var amount: Int = 0

    private val viewModel: BillingViewModel by viewModels()

    @Inject
    lateinit var router: Router
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
        observer()
    }

    private fun init() {
        orderId = intent?.getStringExtra(BuildConfig.KEY_ORDER) ?: ""
        userId = intent?.getStringExtra(BuildConfig.KEY_USER) ?: ""
        amount = intent?.getIntExtra(BuildConfig.KEY_AMOUNT, 0) ?: 0
        matchInfo = intent?.getStringExtra(BuildConfig.KEY_MATCH) ?: ""
        if (orderId.isEmpty() || userId.isEmpty() || amount == 0 || matchInfo.isEmpty()) {
            finish()
            return
        }
        paymentWidget = PaymentWidget(
            activity = this@BillingActivity,
            clientKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm",
            customerKey = userId
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
                amount = PaymentMethod.Rendering.Amount(amount),
                paymentWidgetStatusListener = paymentMethodWidgetStatusListener
            )
            renderAgreement(binding.agreementWidget)
        }
    }

    private fun addListener() {
        binding.payButton.setOnClickListener {
            paymentWidget.requestPayment(
                paymentInfo = PaymentMethod.PaymentInfo(orderId = orderId, orderName = matchInfo),
                paymentCallback = object : PaymentCallback {
                    override fun onPaymentFailed(fail: TossPaymentResult.Fail) {
                        Log.e(
                            javaClass.name.toString(),
                            "errorCode : ${fail.errorCode} , message : ${fail.errorMessage}"
                        )
                    }

                    override fun onPaymentSuccess(success: TossPaymentResult.Success) {
                        viewModel.pay(
                            orderId = orderId,
                            paymentKey = success.paymentKey,
                            orderType = matchInfo
                        )
                    }
                }
            )
        }
    }

    private fun observer() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.actionFlow.collect { state ->
                    when (state) {
                        is BillingViewModel.UiState.Error -> {
                            when (state.errorMessage) {
                                ERROR_SAVE_ERROR -> {
                                    Toast.makeText(
                                        this@BillingActivity,
                                        getString(R.string.msg_app_error),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                ERROR_RE_LOGIN -> {
                                    Toast.makeText(
                                        this@BillingActivity,
                                        getString(R.string.msg_re_login),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    finish()
                                }

                                ERROR_NETWORK_ERROR -> {
                                    Toast.makeText(
                                        this@BillingActivity,
                                        getString(R.string.msg_network_error),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }

                        is BillingViewModel.UiState.Pay -> {
                            val bundle = Bundle().apply {
                                putString(BuildConfig.KEY_MATCH, matchInfo)
                            }
                            router.navigation(NAV_PAY_FINISH, bundle)
                        }
                    }
                }
            }
        }
    }

}