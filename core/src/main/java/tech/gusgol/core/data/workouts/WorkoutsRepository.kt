package tech.gusgol.core.data.workouts

import kotlinx.coroutines.flow.Flow
import tech.gusgol.core.data.Result
import tech.gusgol.core.model.Exercise
import tech.gusgol.core.model.Workout
import tech.gusgol.core.model.WorkoutWithExercises
import javax.inject.Inject

interface WorkoutsRepository {
    suspend fun getWorkouts(): Result<List<Workout>>
    fun getWorkoutsStream(): Flow<List<Workout>>
    fun getWorkoutStream(workoutId: String): Flow<Workout?>
    fun getWorkoutWithExercisesStream(workoutId: String): Flow<List<WorkoutWithExercises>>
    suspend fun insertWorkout(workout: Workout): Result<String>
}

class DefaultWorkoutsRepository @Inject constructor(
    private val localDataSource: WorkoutsDataSource
) : WorkoutsRepository {

    override suspend fun getWorkouts(): Result<List<Workout>> = localDataSource.getWorkouts()

    override fun getWorkoutsStream(): Flow<List<Workout>> = localDataSource.getWorkoutsStream()

    override fun getWorkoutStream(workoutId: String): Flow<Workout?> =
        localDataSource.getWorkoutStream(workoutId)

    override fun getWorkoutWithExercisesStream(workoutId: String): Flow<List<WorkoutWithExercises>> =
        localDataSource.getWorkoutWithExercisesStream(workoutId)

    override suspend fun insertWorkout(workout: Workout): Result<String> =
        localDataSource.insertWorkout(workout)
}