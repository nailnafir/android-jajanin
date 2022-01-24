package com.nailnafir.jajanin.ui.order.detailorder

import com.nailnafir.jajanin.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class OrderDetailPresenter(private val view: OrderDetailContract.View) : OrderDetailContract.Presenter {
    private val mCompositeDisposable: CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getUpdateTransaction(id: String, status: String) {
        view.showLoading()

        // hubungkan antara endpoint dan retrofit
        val disposable = HttpClient.getInstance().getAPI()!!.transactionUpdate(id, status)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success")) {
                        view.onUpdateTransactionSuccess(it.meta?.message.toString())
                    } else {
                        view.onUpdateTransactionFailed(it.meta?.message.toString())
                    }
                },
                {
                    view.dismissLoading()
                    view.onUpdateTransactionFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        mCompositeDisposable!!.clear()
    }

}