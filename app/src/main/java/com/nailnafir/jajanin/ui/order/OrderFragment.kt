package com.nailnafir.jajanin.ui.order

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nailnafir.jajanin.R
import com.nailnafir.jajanin.model.response.transaction.Data
import com.nailnafir.jajanin.model.response.transaction.TransactionResponse
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class OrderFragment : Fragment(), OrderContract.View {

    lateinit var presenter: OrderPresenter
    var progressDialog: Dialog? = null
    var inProgressList: ArrayList<Data>? = ArrayList()
    var pastOrderList: ArrayList<Data>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_order, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
        presenter = OrderPresenter(this)
        presenter.getTransaction()
    }

    private fun initView() {
        // memanggil view dialog loading
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        include_toolbar.toolbar.title = "Jajan Kamu"
        include_toolbar.toolbar.subtitle = "Rincian semua jajan kamu"
    }

    override fun onTransactionSuccess(transactionResponse: TransactionResponse) {
        // validasi isi data transaksi
        if (transactionResponse.data.isNullOrEmpty()) {
            cl_empty.visibility = View.VISIBLE
            cl_tab.visibility = View.GONE
            include_toolbar.visibility = View.GONE
        } else {
            // buat list data
            for (item in transactionResponse.data.indices) {
                // masukkan list data ke dalam tabbar sesuai status
                if (transactionResponse.data[item].status.equals(
                        "ON_DELIVERY",
                        true
                    ) || transactionResponse.data[item].status.equals("PENDING", true)
                ) {
                    inProgressList?.add(transactionResponse.data[item])
                } else if (transactionResponse.data[item].status.equals(
                        "DELIVERED",
                        true
                    ) || transactionResponse.data[item].status.equals(
                        "CANCELLED",
                        true
                    ) || transactionResponse.data[item].status.equals("SUCCESS", true)
                ) {
                    pastOrderList?.add(transactionResponse.data[item])
                }
            }

            // kirim data ke adapter tab
            val sectionPagerAdapter = SectionPagerAdapter(
                childFragmentManager
            )
            sectionPagerAdapter.setData(inProgressList, pastOrderList)
            view_pager.adapter = sectionPagerAdapter
            tab_layout.setupWithViewPager(view_pager)
        }
    }

    override fun onTransactionFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}