package com.nailnafir.jajanin.ui.order.inprogress

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nailnafir.jajanin.R
import com.nailnafir.jajanin.model.response.transaction.Data
import com.nailnafir.jajanin.utils.Helpers.formatPrice
import kotlinx.android.synthetic.main.item_in_progress.view.*

class InProgressAdapter(
    private val listData: List<Data>,
    private val itemAdapterCallback: ItemAdapterCallback,
) : RecyclerView.Adapter<InProgressAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InProgressAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_in_progress, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: InProgressAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Data, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
                tv_title.text = data.food.name
                tv_quantity.text = data.quantity.toString() + "x"
                tv_price.formatPrice(data.food.price.toString())

                Glide.with(context)
                    .load(data.food.picturePath)
                    .into(iv_poster)

                itemView.setOnClickListener {
                    itemAdapterCallback.onClick(it, data)
                }
            }
        }
    }

    interface ItemAdapterCallback {
        fun onClick(v: View, data: Data)
    }
}