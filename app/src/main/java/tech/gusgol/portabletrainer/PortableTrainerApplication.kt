package tech.gusgol.portabletrainer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * [Application] class for Portable Trainer
 */
@HiltAndroidApp
class PortableTrainerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(this)
    }
}