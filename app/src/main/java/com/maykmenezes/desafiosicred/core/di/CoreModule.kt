package com.maykmenezes.desafiosicred.core.di

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

object CoreModule {
    val coreModule = module {
        factory { Dispatchers.IO }
    }
}