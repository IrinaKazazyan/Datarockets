package com.example.datarockets.model

import com.example.datarockets.model.Fermentation
import com.example.datarockets.model.MashTemp

data class Method(
    val fermentation: Fermentation,
    val mash_temp: List<MashTemp>,
    val twist: Any
)