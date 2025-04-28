package com.example.practicabooksoffline.api


import com.example.practicabooksoffline.api.dto.MovieDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieService {
    @GET("movies")
    suspend fun searchMovies(@Query("search") query: String): List<
            MovieDto>

    @GET("movies/{id}")
    suspend fun getMovieDetails(@Path("id") movieId: Int): MovieDto

}