package tech.gusgol.portabletrainer.data.workouts

import tech.gusgol.portabletrainer.data.Result
import tech.gusgol.portabletrainer.model.Workout

interface WorkoutsRepository {
    suspend fun getWorkouts(): Result<List<Workout>>
    suspend fun insertWorkout(workout: Workout): Result<Long>
}

class DefaultWorkoutsRepository(
    private val localDataSource: WorkoutsDataSource
) : WorkoutsRepository {

    override suspend fun getWorkouts(): Result<List<Workout>> = localDataSource.getWorkouts()

    override suspend fun insertWorkout(workout: Workout): Result<Long> =
        localDataSource.insertWorkout(workout)
}