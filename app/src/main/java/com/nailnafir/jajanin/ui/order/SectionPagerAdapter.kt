package com.nailnafir.jajanin.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nailnafir.jajanin.model.response.transaction.Data
import com.nailnafir.jajanin.ui.order.inprogress.InProgressFragment
import com.nailnafir.jajanin.ui.order.pastorders.PastOrderFragment

class SectionPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var inProgressList: ArrayList<Data>? = ArrayList()
    var pastOrderList: ArrayList<Data>? = ArrayList()

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Dalam Proses"
            1 -> "Riwayat Pembelian"
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
                fragment = InProgressFragment()

                // kirim data dengan bundle
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", inProgressList)
                fragment.arguments = bundle

                return fragment
            }
            1 -> {
                fragment = PastOrderFragment()

                // kirim data dengan bundle
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", pastOrderList)
                fragment.arguments = bundle

                return fragment
            }
            else -> {
                fragment = InProgressFragment()

                // kirim data dengan bundle
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", inProgressList)
                fragment.arguments = bundle

                return fragment
            }
        }
    }

    fun setData(inProgressListParms: ArrayList<Data>?, pastOrderListParms: ArrayList<Data>?) {
        inProgressList = inProgressListParms
        pastOrderList = pastOrderListParms
    }
}