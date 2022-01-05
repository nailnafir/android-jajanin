package com.nailnafir.jajanin.ui.profile.information

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nailnafir.jajanin.R
import com.nailnafir.jajanin.model.dummy.ProfileMenuModel
import com.nailnafir.jajanin.ui.profile.ProfileMenuAdapter
import kotlinx.android.synthetic.main.fragment_profile_account.*

class ProfileInformationFragment : Fragment(), ProfileMenuAdapter.ItemAdapterCallback {

    private var menuArrayList: ArrayList<ProfileMenuModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initDataDummy()

        var adapter = ProfileMenuAdapter(menuArrayList, this)
        var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(activity)
        rc_list.layoutManager = layoutManager
        rc_list.adapter = adapter
    }

    private fun initDataDummy() {
        menuArrayList = ArrayList()
        menuArrayList.add(ProfileMenuModel("Nilai Aplikasi"))
        menuArrayList.add(ProfileMenuModel("Bantuan"))
        menuArrayList.add(ProfileMenuModel("Kebijakan dan Privasi"))
        menuArrayList.add(ProfileMenuModel("Syarat dan Ketentuan"))
    }

    override fun onClick(v: View, data: ProfileMenuModel) {
        Toast.makeText(context, "List Menu: " + data.title, Toast.LENGTH_LONG).show()
    }
}