package com.nailnafir.jajanin.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nailnafir.jajanin.model.response.home.Data
import com.nailnafir.jajanin.ui.home.newtaste.HomeNewTasteFragment
import com.nailnafir.jajanin.ui.home.popular.HomePopularFragment
import com.nailnafir.jajanin.ui.home.recommended.HomeRecommendedFragment

class SectionPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var newTasteList:ArrayList<Data>? = ArrayList()
    var popularList:ArrayList<Data>? = ArrayList()
    var recommendedList:ArrayList<Data>? = ArrayList()

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Rasa Baru"
            1 -> "Populer"
            2 -> "Rekomendasi"
            else -> ""
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment
        return when (position) {
            0 -> {
                fragment = HomeNewTasteFragment()

                // setelah data didapatkan dari home, kirimkan ke fragment masing-masing
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", newTasteList)
                fragment.arguments = bundle

                return fragment
            }
            1 -> {
                fragment = HomePopularFragment()

                // setelah data didapatkan dari home, kirimkan ke fragment masing-masing
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", popularList)
                fragment.arguments = bundle

                return fragment
            }
            2 -> {
                fragment = HomeRecommendedFragment()

                // setelah data didapatkan dari home, kirimkan ke fragment masing-masing
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", recommendedList)
                fragment.arguments = bundle

                return fragment
            }
            else -> {
                fragment = HomeNewTasteFragment()

                // setelah data didapatkan dari home, kirimkan ke fragment masing-masing
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", newTasteList)
                fragment.arguments = bundle

                return fragment
            }
        }
    }

    fun setData(newTasteListParms: ArrayList<Data>?, popularListParms: ArrayList<Data>?, recommendedListParms: ArrayList<Data>?) {
        newTasteList = newTasteListParms
        popularList = popularListParms
        recommendedList = recommendedListParms
    }
}