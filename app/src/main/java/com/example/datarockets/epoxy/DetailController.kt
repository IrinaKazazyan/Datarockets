package com.example.datarockets.epoxy


import android.util.Log
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.carousel
import com.example.datarockets.constants.LOG_TAG
import com.example.datarockets.model.BeersListItem
import okhttp3.internal.wait
import java.util.*

class DetailController : TypedEpoxyController<BeersListItem>() {

    override fun buildModels(data: BeersListItem?) {
        Log.d(LOG_TAG, "data buildModels $data")
        data?.let { beers ->
            detailHeader {
                id(beers.id)
                name(beers.name)
                description(beers.description)
                tagLine(beers.tagline)
                imageUrl(beers.image_url)
            }

            detailYeast {
                id(UUID.randomUUID().toString())
                yeast(beers.ingredients?.yeast)
            }

            detailYeast {
                id(UUID.randomUUID().toString())
                yeast("Malts")
            }

            val maltsModels = beers.ingredients?.malt?.map { details ->
                DetailMaltsModel_()
                    .id(details.amount.value)
                    .maltName(details.name)
                    .maltAmountUnit(details.amount.unit)
                    .maltAmountValue(details.amount.value.toString())
            }

            carousel {
                id("malts")
                padding(Carousel.Padding.dp(8, 4, 8, 16, 8))
                hasFixedSize(true)
                models(maltsModels!!)
            }

            detailYeast {
                id(UUID.randomUUID().toString())
                yeast("Hops")
            }

            val hopsModels = beers.ingredients?.hops?.map { details ->
                DetailHopsModel_()
                    .id(details.amount.value)
                    .hopsName(details.name)
                    .hopsAmountUnit(details.amount.unit)
                    .hopsAmountValue(details.amount.value.toString())
                    .hopsAdd(details.add)
                    .hopsAttribute(details.attribute)
            }

            carousel {
                id("hops")
                padding(Carousel.Padding.dp(8, 4, 8, 16, 8))
                hasFixedSize(true)
                models(hopsModels!!)
            }
        }
    }
}