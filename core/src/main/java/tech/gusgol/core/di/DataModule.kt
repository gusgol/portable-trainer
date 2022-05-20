package tech.gusgol.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.gusgol.core.data.workouts.DefaultWorkoutsRepository
import tech.gusgol.core.data.workouts.WorkoutsDataSource
import tech.gusgol.core.data.workouts.WorkoutsLocalDataSource
import tech.gusgol.core.data.workouts.WorkoutsRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun provideWorkoutsRepository(
        workoutsRepository: DefaultWorkoutsRepository
    ): WorkoutsRepository

    @Binds
    fun provideWorkoutsDataSource(
        workoutsLocalDataSource: WorkoutsLocalDataSource
    ): WorkoutsDataSource
}