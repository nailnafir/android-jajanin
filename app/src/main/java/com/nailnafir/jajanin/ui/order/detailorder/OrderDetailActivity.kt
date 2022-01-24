package com.nailnafir.jajanin.ui.order.detailorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.navigation.Navigation
import com.nailnafir.jajanin.R
import kotlinx.android.synthetic.main.layout_toolbar.*

class OrderDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        intent.extras?.let {
            val navController =
                Navigation.findNavController(findViewById(R.id.detail_order_host_fragment))
            val bundle = Bundle()
            bundle.putParcelable("data", it.get("data") as Parcelable)
            navController.setGraph(navController.graph, bundle)
        }
    }

    fun toolbarPayment() {
        toolbar.visibility = View.VISIBLE
        toolbar.title = "Pembayaran"
        toolbar.subtitle = "Detail yang harus dibayar"
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_000)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}