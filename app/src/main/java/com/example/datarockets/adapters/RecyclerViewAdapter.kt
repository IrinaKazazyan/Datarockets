package com.example.datarockets.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.datarockets.R
import com.example.datarockets.model.BeersListItem

class RecyclerViewAdapter(private val onItemClickListener: OnBeerClickListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    //    var beerList = mutableListOf<BeersListItem>()
    var beerList = listOf<BeersListItem>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(beerList[position])

        holder.itemView.setOnClickListener {
            onItemClickListener.itemClicked(beerList[position])
        }
    }

    override fun getItemCount(): Int {
        return beerList.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvName = view.findViewById<TextView>(R.id.tv_name)
        private val tvTagline = view.findViewById<TextView>(R.id.tv_tagline)
        private val tvAbv = view.findViewById<TextView>(R.id.tv_abv)
        private val tvIbu = view.findViewById<TextView>(R.id.tv_ibu)

        fun bind(data: BeersListItem) {
            tvName.text = data.name
            tvTagline.text = data.tagline
            tvAbv.text = data.abv.toString()
            tvIbu.text = data.ibu.toString()
        }
    }

    fun updateList(newItems: List<BeersListItem>) {
        beerList = newItems
        notifyDataSetChanged();
    }

    interface OnBeerClickListener {
        fun itemClicked(beersListItem: BeersListItem)
    }
}