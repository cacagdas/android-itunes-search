package com.cacagdas.itunessearchapp.di

import com.cacagdas.itunessearchapp.ui.detail.DetailFragment
import com.cacagdas.itunessearchapp.ui.search.SearchFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMovieFragment(): DetailFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment
}
