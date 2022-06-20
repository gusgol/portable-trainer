package tech.gusgol.core.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tech.gusgol.core.model.Exercise
import tech.gusgol.core.model.Workout
import tech.gusgol.core.model.WorkoutWithExercises

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout")
    fun getAll(): List<Workout>

    @Query("SELECT * FROM workout")
    fun getAllStream(): Flow<List<Workout>>

    @Query("SELECT * FROM workout WHERE uid = :workoutId")
    fun getWorkout(workoutId: String): Flow<Workout?>

    @Transaction
    @Query("SELECT * FROM workout WHERE uid = :workoutId")
    fun getWorkoutWithExercises(workoutId: String): Flow<List<WorkoutWithExercises>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(workout: Workout): Long

    @Delete
    fun delete(workout: Workout): Int
}