package tech.gusgol.core.data.domain

import tech.gusgol.core.data.Result
import tech.gusgol.core.data.workouts.WorkoutsRepository
import tech.gusgol.core.model.Workout
import java.util.*
import javax.inject.Inject

class ArchiveWorkoutUseCase @Inject constructor(
    private val workoutsRepository: WorkoutsRepository
) {

    suspend operator fun invoke(workout: Workout): Result<Boolean> {
        workout.archived = true
        workout.archivedDate = Date()
        return workoutsRepository.updateWorkout(workout)
    }
}