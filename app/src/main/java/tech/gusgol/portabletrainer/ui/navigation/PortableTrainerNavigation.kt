package tech.gusgol.portabletrainer.ui.navigation

object PortableTrainerDestinations {
    // Workout
    const val WORKOUT_CREATE_ROUTE = "workout/create"
    const val WORKOUTS_ACTIVE = "workouts/active"
    const val WORKOUTS_ARCHIVED = "workouts/archived"
}


interface PortableTrainerDestination {
    val route: String
}