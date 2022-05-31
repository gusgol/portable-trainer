package tech.gusgol.core.data.exercises

import tech.gusgol.core.data.Result
import tech.gusgol.core.model.Exercise
import javax.inject.Inject

interface ExercisesRepository {
    suspend fun insertExercise(exercise: Exercise): Result<String>

}

class DefaultExerciseRepository @Inject constructor(
    private val localDataSource: ExerciseDataSource
): ExercisesRepository {

    override suspend fun insertExercise(exercise: Exercise): Result<String> =
        localDataSource.insertExercise(exercise)
}