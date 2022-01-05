package com.nailnafir.jajanin.ui.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nailnafir.jajanin.ui.profile.account.ProfileAccountFragment
import com.nailnafir.jajanin.ui.profile.information.ProfileInformationFragment

class SectionPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Akun"
            1 -> "Informasi"
            else -> ""
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment
        return when (position) {
            0 -> {
                fragment = ProfileAccountFragment()
                return fragment
            }
            1 -> {
                fragment = ProfileInformationFragment()
                return fragment
            }
            else -> {
                fragment = ProfileAccountFragment()
                return fragment
            }
        }
    }
}