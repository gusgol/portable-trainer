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

    @Query("SELECT * FROM workout WHERE uid = :workoutId")
    fun getWorkout(workoutId: String): Flow<Workout?>

//    @Query(
//        "SELECT * FROM workout JOIN exercise ON workout.uid = exercise.workout_id WHERE workout.uid = :workoutId")
//    fun getWorkoutWithExercises(workoutId: String): Flow<Map<Workout, List<Exercise>>>

    @Transaction
    @Query("SELECT * FROM workout WHERE uid = :workoutId")
    fun getWorkoutWithExercises(workoutId: String): Flow<List<WorkoutWithExercises>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(workout: Workout): Long

    @Delete
    fun delete(workout: Workout): Int
}