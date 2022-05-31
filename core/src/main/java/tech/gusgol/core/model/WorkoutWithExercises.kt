package tech.gusgol.core.model

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutWithExercises(
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "uid",
        entityColumn = "workout_id"
    )
    val exercises: List<Exercise>
)