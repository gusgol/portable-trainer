package tech.gusgol.portabletrainer.domain

import kotlinx.coroutines.flow.Flow
import tech.gusgol.portabletrainer.data.workouts.WorkoutsRepository
import tech.gusgol.portabletrainer.model.Workout

class ObserveWorkoutUseCase(
    private val workoutsRepository: WorkoutsRepository
) {
    operator fun invoke(workoutId: String): Flow<Workout?> =
        workoutsRepository.getWorkoutStream(workoutId)
}