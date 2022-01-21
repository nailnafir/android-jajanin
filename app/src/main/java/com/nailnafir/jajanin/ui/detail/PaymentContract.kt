package com.nailnafir.jajanin.ui.detail

import android.view.View
import com.nailnafir.jajanin.base.BasePresenter
import com.nailnafir.jajanin.base.BaseView
import com.nailnafir.jajanin.model.response.checkout.CheckoutResponse

interface PaymentContract {
    interface View : BaseView {
        fun onCheckoutSuccess(checkoutResponse: CheckoutResponse, view: android.view.View)
        fun onCheckoutFailed(message: String)
    }

    interface Presenter : PaymentContract, BasePresenter {
        fun getCheckout(
            foodId: String,
            userId: String,
            quantity: String,
            total: String,
            view: android.view.View
        )
    }
}