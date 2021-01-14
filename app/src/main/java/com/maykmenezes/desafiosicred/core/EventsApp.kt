package com.maykmenezes.desafiosicred.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.KoinContextHandler
import org.koin.core.context.startKoin

class EventsApp : Application(){
    override fun onCreate() {
        super.onCreate()

        if(KoinContextHandler.getOrNull() == null) {
            startKoin {
                androidContext(this@EventsApp)
            }
        }
    }
}