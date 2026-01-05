package com.devhub.moviesapp.utils

import java.util.Locale

    fun Double?.toRatingString(): String {
        return String.format(Locale.ENGLISH, "%.1f", this ?: 0.0)
    }