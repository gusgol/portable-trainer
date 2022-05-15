package tech.gusgol.portabletrainer.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tech.gusgol.portabletrainer.model.Workout

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout")
    fun getAll(): List<Workout>

    @Query("SELECT * FROM workout WHERE uid = :workoutId")
    fun getWorkout(workoutId: String): Flow<Workout?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(workout: Workout): Long

    @Delete
    fun delete(workout: Workout): Int
}