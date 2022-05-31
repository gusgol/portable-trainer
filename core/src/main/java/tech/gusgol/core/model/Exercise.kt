package tech.gusgol.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Exercise(
    @PrimaryKey val uid: String = UUID.randomUUID().toString(),
    val name: String,
    val sets: Int?,
    val reps: Int?,
    val weight: Int?,
    @ColumnInfo(name = "workout_id") val workoutId: String
)
