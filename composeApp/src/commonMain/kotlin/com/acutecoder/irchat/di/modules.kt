package com.acutecoder.irchat.di

import com.acutecoder.irchat.data.repository.DummyIRModelsRepositoryImpl
import com.acutecoder.irchat.domain.repository.IRModelsRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val testModules = module {
    single {
        DummyIRModelsRepositoryImpl()
    }.bind(IRModelsRepository::class)
}
