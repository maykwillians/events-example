package com.maykmenezes.desafiosicred.features.eventsList.di

import com.maykmenezes.desafiosicred.core.ServiceConfig.retrofit
import com.maykmenezes.desafiosicred.features.eventsList.repository.EventsListRepository
import com.maykmenezes.desafiosicred.features.eventsList.repository.EventsListRepositoryImpl
import com.maykmenezes.desafiosicred.features.eventsList.service.EventsListService
import com.maykmenezes.desafiosicred.features.eventsList.view.EventsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object EventsListModule {
    val eventsListModule = module {
        single<EventsListService> {
            retrofit.create(EventsListService::class.java)
        }
        factory<EventsListRepository> {
            EventsListRepositoryImpl(service = get())
        }
        viewModel {
            EventsListViewModel(repository = get())
        }
    }
}