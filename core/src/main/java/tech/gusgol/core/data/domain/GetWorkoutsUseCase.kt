package tech.gusgol.core.data.domain

import tech.gusgol.core.data.Result
import tech.gusgol.core.data.workouts.WorkoutsRepository
import tech.gusgol.core.model.Workout

class GetWorkoutsUseCase(
    private val workoutsRepository: WorkoutsRepository
) {

    suspend operator fun invoke(): Result<List<Workout>> {
        return workoutsRepository.getWorkouts()
    }
}