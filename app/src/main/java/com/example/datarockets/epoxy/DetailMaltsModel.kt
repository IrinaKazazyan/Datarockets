package com.example.datarockets.epoxy

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.datarockets.R

@EpoxyModelClass(layout = R.layout.detail_malts_layout)
abstract class DetailMaltsModel : EpoxyModelWithHolder<DetailMaltsModel.DetailMaltsHolder>() {


    @EpoxyAttribute
    lateinit var maltName: String

    @EpoxyAttribute
    lateinit var maltAmountUnit: String

    @EpoxyAttribute
    lateinit var maltAmountValue: String

    override fun bind(holder: DetailMaltsHolder) {
        holder.detailMaltNameTextView.text = maltName
        holder.detailMaltAmountUnitTextView.text = maltAmountUnit
        holder.detailMaltAmountValueTextView.text = maltAmountValue
    }

    class DetailMaltsHolder : EpoxyHolder() {

        lateinit var detailMaltNameTextView: TextView
        lateinit var detailMaltAmountUnitTextView: TextView
        lateinit var detailMaltAmountValueTextView: TextView

        override fun bindView(itemView: View) {
            detailMaltNameTextView = itemView.findViewById<TextView>(R.id.detail_malt_name)
            detailMaltAmountUnitTextView =
                itemView.findViewById<TextView>(R.id.detail_malt_amount_unit)
            detailMaltAmountValueTextView =
                itemView.findViewById<TextView>(R.id.detail_malt_amount_value)
        }
    }
}