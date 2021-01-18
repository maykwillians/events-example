package com.maykmenezes.desafiosicred.features.eventDatails.di

import com.maykmenezes.desafiosicred.core.clientService.ServiceConfig.retrofit
import com.maykmenezes.desafiosicred.features.eventDatails.repository.EventDetailsRepository
import com.maykmenezes.desafiosicred.features.eventDatails.repository.EventDetailsRepositoryImpl
import com.maykmenezes.desafiosicred.features.eventDatails.service.EventDetailsService
import com.maykmenezes.desafiosicred.features.eventDatails.view.EventDetailsViewModel
import org.koin.dsl.module

object EventDetailsModule {
    val eventDetailsModule = module {
        single<EventDetailsService> {
            retrofit.create(EventDetailsService::class.java)
        }
        factory<EventDetailsRepository> {
            EventDetailsRepositoryImpl(service = get())
        }
        single {
            EventDetailsViewModel(repository = get(), coroutineDispatcher = get())
        }
    }
}