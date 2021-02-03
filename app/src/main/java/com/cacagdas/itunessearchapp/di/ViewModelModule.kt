package com.cacagdas.itunessearchapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cacagdas.itunessearchapp.ui.detail.DetailViewModel
import com.cacagdas.itunessearchapp.ui.search.SearchViewModel
import com.cacagdas.itunessearchapp.viewmodel.MoviesViewModelFactory

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindRepoViewModel(detailViewModel: DetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: MoviesViewModelFactory): ViewModelProvider.Factory
}
