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
    suspend fun insertWorkout(workout: Workout): Result<String>
    suspend fun updateWorkout(workout: Workout): Result<Boolean>
    fun getWorkoutsStream(archived: Boolean): Flow<List<Workout>>
    fun getWorkoutStream(workoutId: String): Flow<Workout?>
    fun getWorkoutWithExercisesStream(workoutId: String): Flow<List<WorkoutWithExercises>>
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

    override suspend fun updateWorkout(workout: Workout): Result<Boolean> =
        withContext(ioDispatcher) {
            return@withContext try {
                val response = workoutDao.update(workout)
                Result.Success(response == 1)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override fun getWorkoutsStream(archived: Boolean): Flow<List<Workout>> =
        workoutDao.getWorkoutsStream(archived)

    /**
     * TODO change to external entity
     */
    override fun getWorkoutStream(workoutId: String): Flow<Workout?> =
        workoutDao.getWorkout(workoutId)

    override fun getWorkoutWithExercisesStream(workoutId: String): Flow<List<WorkoutWithExercises>> =
        workoutDao.getWorkoutWithExercises(workoutId)
}