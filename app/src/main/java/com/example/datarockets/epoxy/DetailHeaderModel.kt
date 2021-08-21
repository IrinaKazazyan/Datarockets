package com.example.datarockets.epoxy

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import coil.api.load
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.datarockets.R
import java.util.*

@EpoxyModelClass(layout = R.layout.detail_header_layout)
abstract class DetailHeaderModel : EpoxyModelWithHolder<DetailHeaderModel.DetailHeaderHolder>() {


    @EpoxyAttribute
    lateinit var name: String

    @EpoxyAttribute
    lateinit var tagLine: String

    @EpoxyAttribute
    lateinit var description: String

    @EpoxyAttribute
    lateinit var imageUrl: String

    override fun bind(holder: DetailHeaderHolder) {
        holder.detailImageView.load(imageUrl) {}
        holder.detailDescriptionTextView.text = description.toUpperCase(Locale.ROOT)
        holder.detailNameTextView.text = name
        holder.detailTagLineTextView.text = tagLine
    }

    class DetailHeaderHolder : EpoxyHolder() {
        lateinit var detailImageView: ImageView
        lateinit var detailDescriptionTextView: TextView
        lateinit var detailNameTextView: TextView
        lateinit var detailTagLineTextView: TextView

        override fun bindView(itemView: View) {
            detailImageView =
                itemView.findViewById<ImageButton>(R.id.detail_header_image_view)
            detailDescriptionTextView =
                itemView.findViewById<TextView>(R.id.detail_description_text_view)
            detailNameTextView =
                itemView.findViewById<TextView>(R.id.detail_name_text_view)
            detailTagLineTextView =
                itemView.findViewById<TextView>(R.id.detail_tagline_text_view)
        }
    }
}