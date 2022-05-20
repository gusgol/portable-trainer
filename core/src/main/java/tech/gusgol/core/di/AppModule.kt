package tech.gusgol.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.gusgol.core.db.PortableTrainerDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    const val DB_NAME = "portable-trainer-db"

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PortableTrainerDatabase {
        return Room.databaseBuilder(
            context,
            PortableTrainerDatabase::class.java,
            DB_NAME
        ).build()
    }
}