package tech.gusgol.core.data.domain

import kotlinx.coroutines.flow.Flow
import tech.gusgol.core.data.workouts.WorkoutsRepository
import tech.gusgol.core.model.Workout
import javax.inject.Inject

class ObserveWorkoutUseCase @Inject constructor(
    private val workoutsRepository: WorkoutsRepository
) {
    operator fun invoke(workoutId: String): Flow<Workout?> =
        workoutsRepository.getWorkoutStream(workoutId)
}