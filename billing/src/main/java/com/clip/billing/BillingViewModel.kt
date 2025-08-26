package com.clip.billing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clip.domain.useCase.GetPaymentConfirm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillingViewModel @Inject constructor(private val payment: GetPaymentConfirm) : ViewModel() {
    private val _actionFlow = MutableSharedFlow<UiState>()
    val actionFlow: SharedFlow<UiState> = _actionFlow.asSharedFlow()

    fun pay(orderId: String, paymentKey: String, orderType: String) {
        viewModelScope.launch {
            when (val result = payment.invoke(
                GetPaymentConfirm.Param(
                    paymentKey = paymentKey,
                    orderId = orderId,
                    orderType = orderType
                )
            )) {
                is GetPaymentConfirm.Result.Fail -> {
                    _actionFlow.emit(UiState.Error(result.errorMessage))
                }

                is GetPaymentConfirm.Result.Success -> {
                    _actionFlow.emit(UiState.Pay(result.message))
                }
            }
        }
    }

    sealed interface UiState {
        data class Pay(val message: String) : UiState
        data class Error(val errorMessage: String) : UiState
    }
}