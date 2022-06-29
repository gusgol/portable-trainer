package tech.gusgol.core.data.workouts

import kotlinx.coroutines.flow.Flow
import tech.gusgol.core.data.Result
import tech.gusgol.core.model.Workout
import tech.gusgol.core.model.WorkoutWithExercises
import javax.inject.Inject

interface WorkoutsRepository {
    suspend fun getWorkouts(): Result<List<Workout>>
    suspend fun insertWorkout(workout: Workout): Result<String>
    suspend fun updateWorkout(workout: Workout): Result<Boolean>
    fun getWorkoutsStream(archived: Boolean): Flow<List<Workout>>
    fun getWorkoutStream(workoutId: String): Flow<Workout?>
    fun getWorkoutWithExercisesStream(workoutId: String): Flow<List<WorkoutWithExercises>>
}

class DefaultWorkoutsRepository @Inject constructor(
    private val localDataSource: WorkoutsDataSource
) : WorkoutsRepository {

    override suspend fun getWorkouts(): Result<List<Workout>> = localDataSource.getWorkouts()

    override suspend fun updateWorkout(workout: Workout): Result<Boolean> =
        localDataSource.updateWorkout(workout)

    override suspend fun insertWorkout(workout: Workout): Result<String> =
        localDataSource.insertWorkout(workout)

    override fun getWorkoutsStream(archived: Boolean): Flow<List<Workout>> =
        localDataSource.getWorkoutsStream(archived)

    override fun getWorkoutStream(workoutId: String): Flow<Workout?> =
        localDataSource.getWorkoutStream(workoutId)

    override fun getWorkoutWithExercisesStream(workoutId: String): Flow<List<WorkoutWithExercises>> =
        localDataSource.getWorkoutWithExercisesStream(workoutId)
}