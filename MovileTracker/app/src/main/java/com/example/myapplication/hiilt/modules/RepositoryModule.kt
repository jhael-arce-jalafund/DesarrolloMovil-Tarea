package com.example.myapplication.hiilt.modules

import com.example.myapplication.api.MovieService
import com.example.myapplication.db.dao.MovieDao
import com.example.myapplication.repositories.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideBookRepository(
        movieDao: MovieDao,
        movieService: MovieService
    ): MovieRepository {
        return MovieRepository(
            movieDao,
            movieService
        )
    }
}