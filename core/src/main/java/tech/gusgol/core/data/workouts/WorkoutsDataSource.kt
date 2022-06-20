package tech.gusgol.core.data.workouts

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import tech.gusgol.core.data.Result
import tech.gusgol.core.db.WorkoutDao
import tech.gusgol.core.model.Workout
import tech.gusgol.core.model.WorkoutWithExercises
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

interface WorkoutsDataSource {
    suspend fun getWorkouts(): Result<List<Workout>>
    fun getWorkoutsStream(): Flow<List<Workout>>
    fun getWorkoutStream(workoutId: String): Flow<Workout?>
    fun getWorkoutWithExercisesStream(workoutId: String): Flow<List<WorkoutWithExercises>>
    suspend fun insertWorkout(workout: Workout): Result<String>
}

class WorkoutsLocalDataSource @Inject constructor(
    private val workoutDao: WorkoutDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : WorkoutsDataSource {

    override suspend fun getWorkouts(): Result<List<Workout>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(workoutDao.getAll())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun getWorkoutsStream(): Flow<List<Workout>> =
        workoutDao.getAllStream()

    /**
     * TODO change to external entity
     */
    override fun getWorkoutStream(workoutId: String): Flow<Workout?> =
        workoutDao.getWorkout(workoutId)

    override fun getWorkoutWithExercisesStream(workoutId: String): Flow<List<WorkoutWithExercises>> =
        workoutDao.getWorkoutWithExercises(workoutId)

    override suspend fun insertWorkout(workout: Workout): Result<String> = withContext(ioDispatcher) {
        return@withContext try {
            val response = workoutDao.insert(workout)
            if (response == -1L) {
                throw IOException("Failed to insert workout ${workout.name}")
            }
            Result.Success(workout.uid)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}