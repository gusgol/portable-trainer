package tech.gusgol.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.gusgol.core.db.ExerciseDao
import tech.gusgol.core.db.PortableTrainerDatabase
import tech.gusgol.core.db.WorkoutDao

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesWorkoutDao(
        database: PortableTrainerDatabase
    ): WorkoutDao = database.workoutDao()

    @Provides
    fun providesExerciseDado(
       database: PortableTrainerDatabase
    ): ExerciseDao = database.exerciseDao()
}