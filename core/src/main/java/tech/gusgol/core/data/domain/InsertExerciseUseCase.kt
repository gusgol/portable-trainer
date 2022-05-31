package tech.gusgol.core.data.domain

import tech.gusgol.core.data.Result
import tech.gusgol.core.data.exercises.ExercisesRepository
import tech.gusgol.core.model.Exercise
import javax.inject.Inject

class InsertExerciseUseCase @Inject constructor(
    private val exercisesRepository: ExercisesRepository
) {
    suspend operator fun invoke(exercise: Exercise): Result<String> =
        exercisesRepository.insertExercise(exercise)

}