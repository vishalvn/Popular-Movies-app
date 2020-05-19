package com.example.popularmoviesthefinaledition

import com.example.popularmoviesthefinaledition.Data.MovieRepository
import dagger.Component
import javax.inject.Singleton

    @Singleton
    @Component(modules = [(MovieRepository::class)])
    interface ViewModelInjector {
        fun inject (mainViewModel: MainViewModel)
        fun inject(detailViewModel: DetailViewModel)

        @Component.Builder
        interface Builder{
            fun build():ViewModelInjector
            fun movieModule(movieRepository: MovieRepository):Builder
        }
}