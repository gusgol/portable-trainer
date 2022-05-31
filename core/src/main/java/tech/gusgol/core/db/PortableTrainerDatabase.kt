package tech.gusgol.core.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tech.gusgol.core.model.Exercise
import tech.gusgol.core.model.Workout

@Database(
    entities = [Workout::class, Exercise::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class PortableTrainerDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
}

const val DB_NAME = "portable-trainer-db"