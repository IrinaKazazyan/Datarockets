package com.example.datarockets.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datarockets.R
import com.example.datarockets.model.BeersListItem
import kotlinx.android.synthetic.main.item_row.view.*

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var beerList = mutableListOf<BeersListItem>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(beerList[position])
    }

    override fun getItemCount(): Int {
        return beerList.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tv_name = view.tv_name
        val tv_tagline = view.tv_tagline
        val tv_abv = view.tv_abv
        val tv_ibu = view.tv_ibu

        fun bind(data: BeersListItem) {
            tv_name.text = data.name
            tv_tagline.text = data.tagline
            tv_abv.text = data.abv.toString()
            tv_ibu.text = data.ibu.toString()

        }
    }
}