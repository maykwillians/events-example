package com.maykmenezes.desafiosicred.features.eventDatails.di

import com.maykmenezes.desafiosicred.core.ServiceConfig.retrofit
import com.maykmenezes.desafiosicred.features.eventDatails.repository.EventDetailsRepository
import com.maykmenezes.desafiosicred.features.eventDatails.repository.EventDetailsRepositoryImpl
import com.maykmenezes.desafiosicred.features.eventDatails.service.EventDetailsService
import com.maykmenezes.desafiosicred.features.eventDatails.view.EventDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object EventDetailsModule {
    val eventDetailsModule = module {
        single<EventDetailsService> {
            retrofit.create(EventDetailsService::class.java)
        }
        factory<EventDetailsRepository> {
            EventDetailsRepositoryImpl(service = get())
        }
        viewModel {
            EventDetailsViewModel(repository = get())
        }
    }
}