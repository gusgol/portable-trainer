package tech.gusgol.core.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tech.gusgol.core.model.Workout
import tech.gusgol.core.model.WorkoutWithExercises

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout")
    fun getAll(): List<Workout>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(workout: Workout): Long

    @Delete
    fun delete(workout: Workout): Int

    @Update
    fun update(workout: Workout): Int

    @Query("SELECT * FROM workout WHERE archived = :archived")
    fun getWorkoutsStream(archived: Boolean): Flow<List<Workout>>

    @Query("SELECT * FROM workout WHERE uid = :workoutId")
    fun getWorkout(workoutId: String): Flow<Workout?>

    @Transaction
    @Query("SELECT * FROM workout WHERE uid = :workoutId")
    fun getWorkoutWithExercises(workoutId: String): Flow<List<WorkoutWithExercises>>
}