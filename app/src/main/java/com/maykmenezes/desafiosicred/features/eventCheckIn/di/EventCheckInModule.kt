package com.maykmenezes.desafiosicred.features.eventCheckIn.di

import com.maykmenezes.desafiosicred.core.ServiceConfig.retrofit
import com.maykmenezes.desafiosicred.features.eventCheckIn.repository.EventCheckInRepository
import com.maykmenezes.desafiosicred.features.eventCheckIn.repository.EventCheckInRepositoryImpl
import com.maykmenezes.desafiosicred.features.eventCheckIn.service.EventCheckInService
import com.maykmenezes.desafiosicred.features.eventCheckIn.view.EventCheckInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object EventCheckInModule {
    val eventCheckInModule = module {
        single<EventCheckInService> {
            retrofit.create(EventCheckInService::class.java)
        }
        factory<EventCheckInRepository> {
            EventCheckInRepositoryImpl(service = get())
        }
        viewModel {
            EventCheckInViewModel(repository = get())
        }
    }
}