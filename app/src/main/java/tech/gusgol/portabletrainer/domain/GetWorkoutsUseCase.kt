package tech.gusgol.portabletrainer.domain

import tech.gusgol.portabletrainer.data.Result
import tech.gusgol.portabletrainer.data.workouts.WorkoutsRepository
import tech.gusgol.portabletrainer.model.Workout

class GetWorkoutsUseCase(
    private val workoutsRepository: WorkoutsRepository
) {

    suspend operator fun invoke(): Result<List<Workout>> {
        return workoutsRepository.getWorkouts()
    }
}