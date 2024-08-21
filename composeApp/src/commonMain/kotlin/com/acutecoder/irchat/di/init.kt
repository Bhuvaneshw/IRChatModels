package com.acutecoder.irchat.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(block: KoinAppDeclaration? = null) {
    startKoin {
        block?.invoke(this)
        modules(modules)
    }
}
