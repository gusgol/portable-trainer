package tech.gusgol.portabletrainer.data.workouts

import kotlinx.coroutines.flow.Flow
import tech.gusgol.portabletrainer.data.Result
import tech.gusgol.portabletrainer.model.Workout

interface WorkoutsRepository {
    suspend fun getWorkouts(): Result<List<Workout>>
    fun getWorkoutStream(workoutId: String): Flow<Workout?>
    suspend fun insertWorkout(workout: Workout): Result<String>
}

class DefaultWorkoutsRepository(
    private val localDataSource: WorkoutsDataSource
) : WorkoutsRepository {

    override suspend fun getWorkouts(): Result<List<Workout>> = localDataSource.getWorkouts()

    override fun getWorkoutStream(workoutId: String): Flow<Workout?> =
        localDataSource.getWorkoutStream(workoutId)

    override suspend fun insertWorkout(workout: Workout): Result<String> =
        localDataSource.insertWorkout(workout)
}