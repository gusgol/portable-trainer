package tech.gusgol.core.data.domain

import tech.gusgol.core.data.Result
import tech.gusgol.core.data.workouts.WorkoutsRepository
import tech.gusgol.core.model.Workout
import javax.inject.Inject

class InsertWorkoutUseCase @Inject constructor(
    private val workoutsRepository: WorkoutsRepository
) {

    suspend operator fun invoke(workout: Workout): Result<String> {
        return workoutsRepository.insertWorkout(workout)
    }
}