package tech.gusgol.portabletrainer


object PortableTrainerDestinations {
    const val HOME_ROUTE = "home"

    // Workout
    const val WORKOUT_CREATE_ROUTE = "workout/create"
}


interface PortableTrainerDestination {
    val route: String
}