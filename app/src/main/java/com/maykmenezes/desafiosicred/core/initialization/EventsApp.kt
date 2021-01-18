package com.maykmenezes.desafiosicred.core.initialization

import android.app.Application
import com.maykmenezes.desafiosicred.core.di.CoreModule.coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.KoinContextHandler
import org.koin.core.context.startKoin

class EventsApp : Application(){
    override fun onCreate() {
        super.onCreate()

        if(KoinContextHandler.getOrNull() == null) {
            startKoin {
                androidContext(this@EventsApp)
                modules(listOf(
                        coreModule))
            }
        }
    }
}