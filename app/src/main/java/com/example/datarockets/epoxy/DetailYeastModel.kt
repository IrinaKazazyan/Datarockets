package com.example.datarockets.epoxy

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.datarockets.R

@EpoxyModelClass(layout = R.layout.detail_yeast_layout)
abstract class DetailYeastModel : EpoxyModelWithHolder<DetailYeastModel.DetailYeastHolder>() {

    @EpoxyAttribute
    lateinit var yeast: String

    override fun bind(holder: DetailYeastHolder) {
        holder.detailYeast.text = yeast
    }

    class DetailYeastHolder : EpoxyHolder() {

        lateinit var detailYeast: TextView

        override fun bindView(itemView: View) {
            detailYeast = itemView.findViewById(R.id.detail_yeast_text_view)
        }
    }
}