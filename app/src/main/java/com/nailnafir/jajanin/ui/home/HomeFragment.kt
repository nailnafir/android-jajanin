package com.nailnafir.jajanin.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nailnafir.jajanin.databinding.FragmentHomeBinding
import com.nailnafir.jajanin.model.dummy.HomeHorizontalModel
import com.nailnafir.jajanin.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), HomeAdapter.ItemAdapterCallback {
    private var foodList: ArrayList<HomeHorizontalModel> = ArrayList()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // inisialisasi data dummy
        initDataDummy()

        // masukkan data ke dalam adapter
        var adapter = HomeAdapter(foodList, this)
        var layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rc_list.layoutManager = layoutManager
        rc_list.adapter = adapter

        val sectionPagerAdapter = SectionPagerAdapter(childFragmentManager)
        view_pager.adapter = sectionPagerAdapter
        tab_layout.setupWithViewPager(view_pager)
    }

    private fun initDataDummy() {
        foodList = ArrayList()
        foodList.add(HomeHorizontalModel("Takoyaki", "", 5f))
        foodList.add(HomeHorizontalModel("Burger", "", 4f))
        foodList.add(HomeHorizontalModel("Kebab", "", 3.5f))
    }

    override fun onClick(v: View, data: HomeHorizontalModel) {
        val detail = Intent(activity, DetailActivity::class.java)
        startActivity(detail)
    }
}