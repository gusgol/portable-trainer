package tech.gusgol.core.data.workouts

import kotlinx.coroutines.flow.Flow
import tech.gusgol.core.data.Result
import tech.gusgol.core.model.Workout
import javax.inject.Inject

interface WorkoutsRepository {
    suspend fun getWorkouts(): Result<List<Workout>>
    fun getWorkoutStream(workoutId: String): Flow<Workout?>
    suspend fun insertWorkout(workout: Workout): Result<String>
}

class DefaultWorkoutsRepository @Inject constructor(
    private val localDataSource: WorkoutsDataSource
) : WorkoutsRepository {

    override suspend fun getWorkouts(): Result<List<Workout>> = localDataSource.getWorkouts()

    override fun getWorkoutStream(workoutId: String): Flow<Workout?> =
        localDataSource.getWorkoutStream(workoutId)

    override suspend fun insertWorkout(workout: Workout): Result<String> =
        localDataSource.insertWorkout(workout)
}