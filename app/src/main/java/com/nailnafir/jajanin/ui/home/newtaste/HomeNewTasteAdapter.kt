package com.nailnafir.jajanin.ui.home.newtaste

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nailnafir.jajanin.R
import com.nailnafir.jajanin.model.dummy.HomeVerticalModel
import com.nailnafir.jajanin.utils.Helpers.formatPrice
import kotlinx.android.synthetic.main.item_home_vertical.view.*

class HomeNewTasteAdapter(
    private val listData: List<HomeVerticalModel>,
    private val itemAdapterCallback: ItemAdapterCallback,
) : RecyclerView.Adapter<HomeNewTasteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNewTasteAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_home_vertical, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeNewTasteAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: HomeVerticalModel, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
                tv_title.text = data.title
                tv_price.formatPrice(data.price)
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
        fun onClick(v: View, data: HomeVerticalModel)
    }
}