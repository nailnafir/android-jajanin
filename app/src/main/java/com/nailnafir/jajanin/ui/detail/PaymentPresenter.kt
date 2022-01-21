package com.nailnafir.jajanin.ui.detail

import android.view.View
import com.nailnafir.jajanin.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PaymentPresenter(private val view: PaymentContract.View) : PaymentContract.Presenter {
    private val mCompositeDisposable: CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getCheckout(
        foodId: String,
        userId: String,
        quantity: String,
        total: String,
        viewParms: View
    ) {
        view.showLoading()

        // hubungkan antara endpoint dan retrofit
        val disposable = HttpClient.getInstance().getAPI()!!.checkout(
            foodId,
            userId,
            quantity,
            total,
            "DELIVERED"
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success", true)) {
                        it.data?.let { it1 -> view.onCheckoutSuccess(it1, viewParms) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onCheckoutFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onCheckoutFailed(it.message.toString())
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