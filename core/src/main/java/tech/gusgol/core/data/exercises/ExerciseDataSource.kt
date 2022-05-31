package tech.gusgol.core.data.exercises

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.gusgol.core.data.Result
import tech.gusgol.core.db.ExerciseDao
import tech.gusgol.core.model.Exercise
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

interface ExerciseDataSource {
    suspend fun insertExercise(exercise: Exercise): Result<String>
}

class ExerciseLocalDataSource @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ExerciseDataSource {

    override suspend fun insertExercise(exercise: Exercise) = withContext(ioDispatcher) {
        return@withContext try {
            val response = exerciseDao.insert(exercise)
            if (response == -1L) {
                throw IOException("Unable to insert exercise: ${exercise.uid}")
            }
            Result.Success(exercise.uid)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}