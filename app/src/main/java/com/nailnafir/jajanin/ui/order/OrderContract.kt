package com.nailnafir.jajanin.ui.order

import com.nailnafir.jajanin.base.BasePresenter
import com.nailnafir.jajanin.base.BaseView
import com.nailnafir.jajanin.model.response.transaction.TransactionResponse

interface OrderContract {
    interface View: BaseView {
        fun onTransactionSuccess(transactionResponse: TransactionResponse)
        fun onTransactionFailed(message: String)
    }

    interface Presenter: OrderContract, BasePresenter {
        fun getTransaction()
    }
}