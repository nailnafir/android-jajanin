package com.nailnafir.jajanin.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.nailnafir.jajanin.Jajanin
import com.nailnafir.jajanin.R
import com.nailnafir.jajanin.model.response.login.User
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val sectionPagerAdapter = SectionPagerAdapter(childFragmentManager)

        view_pager.adapter = sectionPagerAdapter
        tab_layout.setupWithViewPager(view_pager)

        // ambil data dari shared preference yang tersimpan dari login
        var user = Jajanin.getApp().getUser()
        var userResponse = Gson().fromJson(user, User::class.java)

        // set data dari user yang tersimpan
        tv_name.text = userResponse.name
        tv_email.text = userResponse.email

        if (!userResponse.profile_photo_url.isNullOrEmpty()) {
            Glide.with(requireActivity())
                .load(userResponse.profile_photo_url)
                .into(iv_photo)
        }
    }
}