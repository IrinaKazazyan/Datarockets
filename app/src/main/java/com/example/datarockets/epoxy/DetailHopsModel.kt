package com.example.datarockets.epoxy

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.datarockets.R

@EpoxyModelClass(layout = R.layout.detail_hops_layout)
abstract class DetailHopsModel : EpoxyModelWithHolder<DetailHopsModel.DetailHopsHolder>() {

    @EpoxyAttribute
    lateinit var hopsName: String

    @EpoxyAttribute
    lateinit var hopsAmountUnit: String

    @EpoxyAttribute
    lateinit var hopsAmountValue: String

    @EpoxyAttribute
    lateinit var hopsAdd: String

    @EpoxyAttribute
    lateinit var hopsAttribute: String

    override fun bind(holder: DetailHopsHolder) {
        holder.detailHopsNameTextView.text = hopsName
        holder.detailHopsAmountUnitTextView.text = hopsAmountUnit
        holder.detailHopsAmountValueTextView.text = hopsAmountValue
        holder.detailHopsAddTextView.text = hopsAdd
        holder.detailHopsAttributeTextView.text = hopsAttribute
    }

    class DetailHopsHolder : EpoxyHolder() {

        lateinit var detailHopsNameTextView: TextView
        lateinit var detailHopsAmountUnitTextView: TextView
        lateinit var detailHopsAmountValueTextView: TextView
        lateinit var detailHopsAddTextView: TextView
        lateinit var detailHopsAttributeTextView: TextView

        override fun bindView(itemView: View) {
            detailHopsNameTextView = itemView.findViewById(R.id.detail_hops_name)
            detailHopsAmountUnitTextView =
                itemView.findViewById(R.id.detail_hops_amount_unit)
            detailHopsAmountValueTextView =
                itemView.findViewById(R.id.detail_hops_amount_value)
            detailHopsAddTextView = itemView.findViewById(R.id.detail_hops_add)
            detailHopsAttributeTextView =
                itemView.findViewById(R.id.detail_hops_attribute)
        }
    }
}