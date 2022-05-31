package tech.gusgol.core.data.domain

import kotlinx.coroutines.flow.Flow
import tech.gusgol.core.data.workouts.WorkoutsRepository
import tech.gusgol.core.model.Exercise
import tech.gusgol.core.model.Workout
import tech.gusgol.core.model.WorkoutWithExercises
import javax.inject.Inject

class ObserveWorkoutWithExercisesUseCase @Inject constructor(
    private val workoutsRepository: WorkoutsRepository
) {
    operator fun invoke(workoutId: String): Flow<List<WorkoutWithExercises>> =
        workoutsRepository.getWorkoutWithExercisesStream(workoutId)
}