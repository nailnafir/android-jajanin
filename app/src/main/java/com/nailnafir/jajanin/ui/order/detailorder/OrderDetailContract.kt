package com.nailnafir.jajanin.ui.order.detailorder

import com.nailnafir.jajanin.base.BasePresenter
import com.nailnafir.jajanin.base.BaseView
import com.nailnafir.jajanin.model.response.transaction.TransactionResponse

interface OrderDetailContract {
    interface View: BaseView {
        fun onUpdateTransactionSuccess(message: String)
        fun onUpdateTransactionFailed(message: String)
    }

    interface Presenter: OrderDetailContract, BasePresenter {
        fun getUpdateTransaction(id: String, status: String)
    }
}