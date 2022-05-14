package tech.gusgol.portabletrainer.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import tech.gusgol.portabletrainer.model.Workout

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout")
    fun getAll(): List<Workout>

    @Insert
    fun insert(workout: Workout): Long
}