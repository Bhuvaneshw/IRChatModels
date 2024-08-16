package com.acutecoder.irchat

import android.app.Application
import com.acutecoder.irchat.di.initKoin
import org.koin.android.ext.koin.androidContext

class IRChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@IRChatApplication)
        }

    }

}
