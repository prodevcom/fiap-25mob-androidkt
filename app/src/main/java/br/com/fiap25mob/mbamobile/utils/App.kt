package br.com.fiap25mob.mbamobile.utils

import android.app.Application
import br.com.fiap25mob.mbamobile.di.guitarsDiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        startKoin {
            androidContext(this@App)
            modules(guitarsDiModule)
        }
    }
}
