package com.nailnafir.jajanin.ui.detail

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.nailnafir.jajanin.Jajanin
import com.nailnafir.jajanin.R
import com.nailnafir.jajanin.model.response.checkout.CheckoutResponse
import com.nailnafir.jajanin.model.response.home.Data
import com.nailnafir.jajanin.model.response.login.User
import com.nailnafir.jajanin.utils.Helpers.formatPrice
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.fragment_payment.view.*

class PaymentFragment : Fragment(), PaymentContract.View {

    var progressDialog: Dialog? = null
    lateinit var presenter: PaymentPresenter
    var total: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as DetailActivity).toolbarPayment()

        // terima bundle data dari detail fragment
        val data = arguments?.getParcelable<Data>("data")
        initView(data)
        initView()
        presenter = PaymentPresenter(this)
    }

    private fun initView() {
        // memanggil progress dialog
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        // set progress dialog
        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    private fun initView(data: Data?) {
        // set data
        val deliver_price = 12000
        tv_delivery.formatPrice(deliver_price.toString())

        // ambil data bundle
        val quantity = (arguments?.getString("quantity"))?.toInt()
        tv_quantity.text = quantity.toString() + "x"

        // ambil data json API food
        tv_title.text = data?.name
        tv_price.formatPrice(data?.price.toString())

        Glide.with(requireContext())
            .load(data?.picturePath)
            .into(iv_poster)

        tv_name_item.text = data?.name
        tv_price_item.formatPrice(data?.price.toString())

        // validasi isi data harga
        if (!data?.price.toString().isNullOrEmpty()) {
            val totalTax = data?.price?.div(10)

            tv_tax.formatPrice(totalTax.toString())

            total = quantity!! * data?.price!! + totalTax!! + deliver_price
            tv_total.formatPrice(total.toString())
        } else {
            tv_price.text = "IDR 0"
            tv_tax.text = "IDR 0"
            tv_total.text = "IDR 0"
            total = 0
        }

        // ambil data json user dari shared preference
        var user = Jajanin.getApp().getUser()
        var userResponse = Gson().fromJson(user, User::class.java)

        tv_name.text = userResponse?.name
        tv_phone.text = userResponse?.phoneNumber
        tv_address.text = userResponse?.address
        tv_city.text = userResponse?.city

        button_checkout.setOnClickListener {
            presenter.getCheckout(
                data?.id.toString(),
                userResponse?.id.toString(),
                quantity.toString(),
                total.toString(),
                it
            )
        }
    }

    override fun onCheckoutSuccess(checkoutResponse: CheckoutResponse, view: View) {
        // buka browser midtrans
        val pay = Intent(Intent.ACTION_VIEW)
        pay.data = Uri.parse(checkoutResponse.paymentUrl)
        startActivity(pay)

        // alihkan ke halaman payment success
        Navigation.findNavController(view).navigate(R.id.action_payment_success)
    }

    override fun onCheckoutFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}