package com.nailnafir.jajanin.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.nailnafir.jajanin.R
import com.nailnafir.jajanin.model.response.home.Data
import com.nailnafir.jajanin.utils.Helpers.formatPrice
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as DetailActivity).toolbarDetail()

        // ambil data yang dikirmkan melalu intent (fragment menggunakan arguments)
        arguments?.let {
            DetailFragmentArgs.fromBundle(it).data.let {
                initView(it)
            }
        }

        button_order_now.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_payment)
        }
    }

    private fun initView(data: Data?) {
        Glide.with(requireContext())
            .load(data?.picturePath)
            .into(iv_poster)

        tv_title.text = data?.name
        tv_desc.text = data?.description
        tv_ingredients.text = data?.ingredients

        rating_bar.rating = data?.rate?.toFloat() ?: 0f

        tv_total.formatPrice(data?.price.toString())
    }
}