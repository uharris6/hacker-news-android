package com.uharris.hackernews.di.modules

import com.uharris.hackernews.presentation.sections.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIModule {

    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity
}