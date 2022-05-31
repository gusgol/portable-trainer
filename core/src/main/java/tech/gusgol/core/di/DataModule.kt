package tech.gusgol.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.gusgol.core.data.exercises.DefaultExerciseRepository
import tech.gusgol.core.data.exercises.ExerciseDataSource
import tech.gusgol.core.data.exercises.ExerciseLocalDataSource
import tech.gusgol.core.data.exercises.ExercisesRepository
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

    @Binds
    fun provideExerciseRepository(
        exerciseRepository: DefaultExerciseRepository
    ): ExercisesRepository

    @Binds
    fun provideExerciseDataSource(
        exerciseDataSource: ExerciseLocalDataSource
    ): ExerciseDataSource
}