package com.nailnafir.jajanin.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.nailnafir.jajanin.Jajanin
import com.nailnafir.jajanin.R
import com.nailnafir.jajanin.model.dummy.HomeHorizontalModel
import com.nailnafir.jajanin.model.response.home.Data
import com.nailnafir.jajanin.model.response.home.HomeResponse
import com.nailnafir.jajanin.model.response.login.User
import com.nailnafir.jajanin.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), HomeAdapter.ItemAdapterCallback, HomeContract.View {
    private var newStateList: ArrayList<Data> = ArrayList()
    private var popularList: ArrayList<Data> = ArrayList()
    private var recommendedList: ArrayList<Data> = ArrayList()

    private lateinit var presenter: HomePresenter

    var progressDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // inisialisasi data dummy
        // initDataDummy()
        initView()
        presenter = HomePresenter(this)
        presenter.getHome()
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

        // mengambil data user dari preference memori lalu simpan lagi ke dalam data json
        var user = Jajanin.getApp().getUser()
        var userResponse = Gson().fromJson(user, User::class.java)

        // memanggil view foto profil user yang ada di preference memori
        if (!userResponse.profile_photo_url.isNullOrEmpty()) {
            Glide.with(requireActivity())
                .load(userResponse.profile_photo_url)
                .into(iv_profile)
        }
    }

//    private fun initDataDummy() {
//        foodList = ArrayList()
//        foodList.add(HomeHorizontalModel("Takoyaki", "", 5f))
//        foodList.add(HomeHorizontalModel("Burger", "", 4f))
//        foodList.add(HomeHorizontalModel("Kebab", "", 3.5f))
//    }

    override fun onClick(v: View, data: Data) {
        val detail = Intent(activity, DetailActivity::class.java)
        startActivity(detail)
    }

    override fun onHomeSuccess(homeResponse: HomeResponse) {
        // filter data pada tab kategori
        for (a in homeResponse.data.indices) {
            var items:List<String> = homeResponse.data[a].types?.split(",") ?: ArrayList()

            // jika item lebih dari satu, kirim data ke section page adapter
            for (x in items.indices) {
                if (items[x].equals("new_food", true)) {
                    newStateList.add(homeResponse.data[a])
                } else if (items[x].equals("recommended", true)) {
                    recommendedList.add(homeResponse.data[a])
                } else if (items[x].equals("popular", true)) {
                    popularList.add(homeResponse.data[a])
                }
            }
        }

        // masukkan data ke dalam adapter
        var adapter = HomeAdapter(homeResponse.data, this)
        var layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rc_list.layoutManager = layoutManager
        rc_list.adapter = adapter

        val sectionPagerAdapter = SectionPagerAdapter(childFragmentManager)
        sectionPagerAdapter.setData(newStateList, popularList, recommendedList)

        view_pager.adapter = sectionPagerAdapter
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun onHomeFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}