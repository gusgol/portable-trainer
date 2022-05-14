package tech.gusgol.portabletrainer

import android.app.Application

class PortableTrainerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(this)
    }
}