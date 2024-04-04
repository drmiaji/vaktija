package vk.vaktija

import android.app.Application
import vk.vaktija.di.AppModule
import vk.vaktija.di.AppModuleImpl

class MyApp : Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}