package com.devhub.moviesapp.data.mapper

import com.devhub.moviesapp.data.data_sources.local.FavoriteMovieEntity
import com.devhub.moviesapp.domain.model.Movie


fun FavoriteMovieEntity.toMovie(): Movie {
    return Movie(
        adult = adult ?: false,
        backdrop_path = backdrop_path ?: "",
        original_language = original_language ?: "",
        overview = overview ?: "",
        poster_path = poster_path ?: "",
        release_date = release_date ?: "",
        title = title ?: "",
        vote_average = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        vote_count = vote_count ?: 0,
        id = id ?: -1,
        original_title = original_title ?: "",
        video = video ?: false,

        genre_ids = try {
            genre_ids.split(",").map { it.toInt() }
        } catch (e: Exception) {
            listOf(-1, -2)
        },

        category = "",
    )
}


fun Movie.toFavoriteMovieEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        adult = adult ?: false,
        backdrop_path = backdrop_path ?: "",
        original_language = original_language ?: "",
        overview = overview ?: "",
        poster_path = poster_path ?: "",
        release_date = release_date ?: "",
        title = title ?: "",
        vote_average = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        vote_count = vote_count ?: 0,
        id = id ?: -1,
        original_title = original_title ?: "",
        video = video ?: false,


        genre_ids = try {
            genre_ids.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        }
    )
}