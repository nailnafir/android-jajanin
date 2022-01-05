package com.nailnafir.jajanin.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nailnafir.jajanin.R
import com.nailnafir.jajanin.model.dummy.HomeHorizontalModel
import kotlinx.android.synthetic.main.fragment_home.view.tv_title
import kotlinx.android.synthetic.main.item_home_horizontal.view.*

class HomeAdapter(
    private val listData: List<HomeHorizontalModel>,
    private val itemAdapterCallback: ItemAdapterCallback,
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_home_horizontal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: HomeHorizontalModel, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
                tv_title.text = data.title
                rb_food.rating = data.rating

//                Glide.with(context)
//                    .load(data.src)
//                    .into(iv_poster)

                itemView.setOnClickListener {
                    itemAdapterCallback.onClick(it, data)
                }
            }
        }
    }

    interface ItemAdapterCallback {
        fun onClick(v: View, data: HomeHorizontalModel)
    }
}