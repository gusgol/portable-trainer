package tech.gusgol.portabletrainer.domain

import tech.gusgol.portabletrainer.data.Result
import tech.gusgol.portabletrainer.data.workouts.WorkoutsRepository
import tech.gusgol.portabletrainer.model.Workout

class InsertWorkoutUseCase(
    private val workoutsRepository: WorkoutsRepository
) {

    suspend operator fun invoke(workout: Workout): Result<Long> {
        return workoutsRepository.insertWorkout(workout)
    }
}