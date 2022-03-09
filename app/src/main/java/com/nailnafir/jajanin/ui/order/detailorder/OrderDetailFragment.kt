package com.nailnafir.jajanin.ui.order.detailorder

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.nailnafir.jajanin.R
import com.nailnafir.jajanin.model.response.transaction.Data
import com.nailnafir.jajanin.utils.Helpers.formatPrice
import kotlinx.android.synthetic.main.fragment_order_detail.*
import kotlinx.android.synthetic.main.fragment_order_detail.iv_poster
import kotlinx.android.synthetic.main.fragment_order_detail.tv_delivery
import kotlinx.android.synthetic.main.fragment_order_detail.tv_name_item
import kotlinx.android.synthetic.main.fragment_order_detail.tv_price
import kotlinx.android.synthetic.main.fragment_order_detail.tv_price_item
import kotlinx.android.synthetic.main.fragment_order_detail.tv_quantity
import kotlinx.android.synthetic.main.fragment_order_detail.tv_tax
import kotlinx.android.synthetic.main.fragment_order_detail.tv_title

class OrderDetailFragment : Fragment(), OrderDetailContract.View {

    var progressDialog: Dialog? = null
    lateinit var presenter: OrderDetailPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // ambil data dari nav
        arguments?.let {
            OrderDetailFragmentArgs.fromBundle(it).data.let {
                initView(it)
            }
        }

        // panggil toolbar
        (activity as OrderDetailActivity?)!!.toolbarPayment()
        initView()
        presenter = OrderDetailPresenter(this)
    }

    private fun initView(data: Data?) {
        // set data
        val deliverPrice = 12000
        tv_delivery.formatPrice(deliverPrice.toString())

        // set data dari json api food
        Glide.with(requireContext())
            .load(data?.food?.picturePath)
            .into(iv_poster)

        tv_title.text = data?.food?.name
        tv_price.formatPrice(data?.food?.price.toString())

        tv_name_item.text = data?.food?.name
        tv_price_item.formatPrice(data?.food?.price.toString())

        // set data dari json api user
        tv_name.text = data?.user?.name
        tv_phone.text = data?.user?.phoneNumber
        tv_address.text = data?.user?.address
        tv_house_number.text = data?.user?.houseNumber
        tv_city.text = data?.user?.city

        // set data dari json api transaction
        tv_id.text = "#JJ" + data?.id.toString()
        tv_quantity.text = data?.quantity.toString() + "x"

        // validasi isi data
        if (!data?.food?.price.toString().isNullOrEmpty()) {
            var totalTax = data?.food?.price?.div(10)
            tv_tax.formatPrice(totalTax.toString())
            var total = data?.food?.price!! + totalTax!! + deliverPrice
            tv_total.formatPrice(total.toString())
        } else {
            tv_price.text = "IDR 0"
            tv_tax.text = "IDR 0"
            tv_total.text = "IDR 0"
        }

        // validasi status pembayaran
        if (data?.status.equals("ON_DELIVERY", true)) {
            button_pay_or_cancel.visibility = View.VISIBLE
            cl_order_info.visibility = View.VISIBLE
            tv_status.text = "Sudah Dibayar"
        } else if (data?.status.equals("SUCCESS", true)) {
            button_pay_or_cancel.visibility = View.INVISIBLE
            cl_order_info.visibility = View.VISIBLE
            tv_status.text = "Sudah Dibayar"
        } else if (data?.status.equals("PENDING", true)) {
            button_pay_or_cancel.visibility = View.VISIBLE
            button_pay_or_cancel.text = "Bayar Sekarang"
            button_pay_or_cancel.setTextColor(resources.getColor(R.color.black))
            button_pay_or_cancel.setBackgroundColor(resources.getColor(R.color.yellow))
            cl_order_info.visibility = View.VISIBLE
            tv_status.text = "Belum Dibayar"
            tv_status.setTextColor(resources.getColor(R.color.green))
        }

        // aksi tombol jika belum dibayar atau sudah dibayar
        button_pay_or_cancel.setOnClickListener {
            if (button_pay_or_cancel.text.equals("Bayar Sekarang")) {
                val pay = Intent(Intent.ACTION_VIEW)
                pay.data = Uri.parse(data?.paymentUrl)
                startActivity(pay)
            } else {
                presenter.getUpdateTransaction(data?.id.toString(), "CANCELLED")
            }
        }
    }

    private fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onUpdateTransactionSuccess(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        requireActivity().finish()
    }

    override fun onUpdateTransactionFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}