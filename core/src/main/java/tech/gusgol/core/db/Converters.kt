package tech.gusgol.core.db

import androidx.room.TypeConverter
import tech.gusgol.core.model.WorkoutIcon
import java.util.*

class Converters {

    @TypeConverter
    fun stringToWorkoutIcon(value: String?): WorkoutIcon? {
        return value?.let { WorkoutIcon.valueOf(it) }
    }

    @TypeConverter
    fun workoutIconToString(workoutIcon: WorkoutIcon?): String? {
        return workoutIcon?.name
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}