package tech.gusgol.core.data.domain

import kotlinx.coroutines.flow.Flow
import tech.gusgol.core.data.workouts.WorkoutsRepository
import tech.gusgol.core.model.Workout

class ObserveWorkoutUseCase(
    private val workoutsRepository: WorkoutsRepository
) {
    operator fun invoke(workoutId: String): Flow<Workout?> =
        workoutsRepository.getWorkoutStream(workoutId)
}