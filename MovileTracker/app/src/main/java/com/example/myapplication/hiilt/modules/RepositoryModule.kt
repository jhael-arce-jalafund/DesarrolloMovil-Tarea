package com.example.practicabooksoffline.hiilt.modules

import com.example.practicabooksoffline.api.MovieService
import com.example.practicabooksoffline.db.dao.MovieDao
import com.example.practicabooksoffline.repositories.MovieRepository
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