package tech.gusgol.core.data.domain

import kotlinx.coroutines.flow.Flow
import tech.gusgol.core.data.workouts.WorkoutsRepository
import tech.gusgol.core.model.Workout
import javax.inject.Inject

class ObserveWorkoutsUseCase @Inject constructor(
    private val workoutsRepository: WorkoutsRepository
) {

    operator fun invoke(): Flow<List<Workout>> {
        return workoutsRepository.getWorkoutsStream()
    }
}