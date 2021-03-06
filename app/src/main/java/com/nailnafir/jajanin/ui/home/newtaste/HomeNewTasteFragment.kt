package com.nailnafir.jajanin.ui.home.newtaste

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nailnafir.jajanin.R
import com.nailnafir.jajanin.model.dummy.HomeVerticalModel
import com.nailnafir.jajanin.model.response.home.Data
import com.nailnafir.jajanin.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeNewTasteFragment : Fragment(), HomeNewTasteAdapter.ItemAdapterCallback {

//    private var foodList: ArrayList<HomeVerticalModel> = ArrayList()
    private var newTasteList: ArrayList<Data>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_new_taste, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        newTasteList = arguments?.getParcelableArrayList("data")

//        initDataDummy()

        var adapter = HomeNewTasteAdapter(newTasteList!!, this)
        var layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(activity)
        rc_list.layoutManager = layoutManager
        rc_list.adapter = adapter
    }

//    private fun initDataDummy() {
//        foodList = ArrayList()
//        foodList.add(HomeVerticalModel("Takoyaki", "10000", "", 5f))
//        foodList.add(HomeVerticalModel("Burger", "12000", "", 4f))
//        foodList.add(HomeVerticalModel("Kebab", "18000", "", 3.5f))
//    }

    override fun onClick(v: View, data: Data) {
        val detail = Intent(activity, DetailActivity::class.java).putExtra("data", data)
        startActivity(detail)
    }
}