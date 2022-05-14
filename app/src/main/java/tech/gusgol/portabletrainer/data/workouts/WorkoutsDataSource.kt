package tech.gusgol.portabletrainer.data.workouts

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.gusgol.portabletrainer.data.Result
import tech.gusgol.portabletrainer.db.WorkoutDao
import tech.gusgol.portabletrainer.model.Workout
import java.lang.Exception

interface WorkoutsDataSource {
    suspend fun getWorkouts(): Result<List<Workout>>
    suspend fun insertWorkout(workout: Workout): Result<Long>
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

    override suspend fun insertWorkout(workout: Workout): Result<Long> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(workoutDao.insert(workout))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}