package com.nailnafir.jajanin.ui.order.pastorders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nailnafir.jajanin.R
import com.nailnafir.jajanin.model.response.transaction.Data
import com.nailnafir.jajanin.utils.Helpers.convertLongToTime
import com.nailnafir.jajanin.utils.Helpers.formatPrice
import kotlinx.android.synthetic.main.item_past_order.view.*

class PastOrderAdapter(
    private val listData: List<Data>,
    private val itemAdapterCallback: ItemAdapterCallback,
) : RecyclerView.Adapter<PastOrderAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PastOrderAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_past_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PastOrderAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Data, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
                tv_title.text = data.food.name
                tv_price.formatPrice(data.food.price.toString())
                tv_quantity.text = data.quantity.toString() + "x"

                tv_time.text = data.createdAt.convertLongToTime("MMM dd, HH.mm")
                tv_status.text = data.status

                if (data.status.equals("CANCELLED", true)) {
                    tv_status.setTextColor(resources.getColor(R.color.red))
                } else if (data.status.equals("SUCCESS", true)) {
                    tv_status.setTextColor(resources.getColor(R.color.green))
                } else if (data.status.equals("DELIVERED", true)) {
                    tv_status.setTextColor(resources.getColor(R.color.blue))
                }

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