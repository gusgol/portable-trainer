package tech.gusgol.portabletrainer.data.workouts

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import tech.gusgol.portabletrainer.data.Result
import tech.gusgol.portabletrainer.db.WorkoutDao
import tech.gusgol.portabletrainer.model.Workout
import java.io.IOException
import java.lang.Exception

interface WorkoutsDataSource {
    suspend fun getWorkouts(): Result<List<Workout>>
    fun getWorkoutStream(workoutId: String): Flow<Workout?>
    suspend fun insertWorkout(workout: Workout): Result<String>
}

class WorkoutsLocalDataSource(
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

    /**
     * TODO change to external entity
     */
    override fun getWorkoutStream(workoutId: String): Flow<Workout?> =
        workoutDao.getWorkout(workoutId)

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