package tech.gusgol.portabletrainer.db

import androidx.room.TypeConverter
import tech.gusgol.portabletrainer.model.WorkoutIcon

class Converters {

    @TypeConverter
    fun stringToWorkoutIcon(value: String?): WorkoutIcon? {
        return value?.let { WorkoutIcon.valueOf(it) }
    }

    @TypeConverter
    fun workoutIconToString(workoutIcon: WorkoutIcon?): String? {
        return workoutIcon?.name
    }
}