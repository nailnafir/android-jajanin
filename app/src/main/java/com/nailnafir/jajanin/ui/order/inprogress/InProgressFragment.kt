package com.nailnafir.jajanin.ui.order.inprogress

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.nailnafir.jajanin.R
import com.nailnafir.jajanin.model.response.transaction.Data
import com.nailnafir.jajanin.ui.order.detailorder.OrderDetailActivity
import kotlinx.android.synthetic.main.fragment_in_progress.*

class InProgressFragment : Fragment(), InProgressAdapter.ItemAdapterCallback {

    private var adapter: InProgressAdapter? = null
    var inProgressList: ArrayList<Data>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_in_progress, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // ambil data dari bundle
        inProgressList = arguments?.getParcelableArrayList("data")

        // tampilkan data ke recycler view
        if (!inProgressList.isNullOrEmpty()) {
            adapter = InProgressAdapter(inProgressList!!, this)
            val layoutManager = LinearLayoutManager(activity)

            rc_list.layoutManager = layoutManager
            rc_list.adapter = adapter
        }
    }

    override fun onClick(v: View, data: Data) {
        val detail = Intent(activity, OrderDetailActivity::class.java).putExtra("data", data)
        startActivity(detail)
    }
}