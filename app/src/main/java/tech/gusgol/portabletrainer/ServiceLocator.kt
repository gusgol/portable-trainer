package tech.gusgol.portabletrainer

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import tech.gusgol.core.data.workouts.DefaultWorkoutsRepository
import tech.gusgol.core.data.workouts.WorkoutsDataSource
import tech.gusgol.core.data.workouts.WorkoutsLocalDataSource
import tech.gusgol.core.data.workouts.WorkoutsRepository
import tech.gusgol.core.db.PortableTrainerDatabase
import tech.gusgol.core.data.domain.GetWorkoutsUseCase
import tech.gusgol.core.data.domain.InsertWorkoutUseCase
import tech.gusgol.core.data.domain.ObserveWorkoutUseCase
import tech.gusgol.core.db.provideDatabase
import tech.gusgol.portabletrainer.ui.home.HomeViewModel
import tech.gusgol.portabletrainer.ui.workouts.create.CreateWorkoutViewModel
import tech.gusgol.portabletrainer.ui.workouts.detail.WorkoutDetailViewModel

object ServiceLocator {

    private var db: PortableTrainerDatabase? = null

    fun init(application: Application) {
        db = provideDatabase(application)
    }

    object Workouts {
        fun provideHomeViewModelFactory(): ViewModelProvider.Factory =
            HomeViewModel.provideFactory(
                provideGetWorkoutsUseCase(
                    provideDefaultWorkoutsRepository(
                        provideWorkoutsLocalDataSource()
                    )
                )
            )

        fun provideCreateWorkoutViewModelFactory(): ViewModelProvider.Factory =
            CreateWorkoutViewModel.provideFactory(
                provideInsertWorkoutUseCase(
                    provideDefaultWorkoutsRepository(
                        provideWorkoutsLocalDataSource()
                    )
                )
            )

        fun provideWorkoutDetailViewModelFactory(workoutId: String): ViewModelProvider.Factory =
            WorkoutDetailViewModel.provideFactory(
                provideObserveWorkoutUseCase(
                    provideDefaultWorkoutsRepository(
                        provideWorkoutsLocalDataSource()
                    )
                ),
                workoutId
            )

        private fun provideWorkoutsLocalDataSource(): WorkoutsLocalDataSource {
            return db?.let {
                WorkoutsLocalDataSource(it.workoutDao())
            } ?: throw NullPointerException("db has to be initiated")
        }

        private fun provideDefaultWorkoutsRepository(localDataSource: WorkoutsDataSource) =
            DefaultWorkoutsRepository(
                localDataSource = localDataSource
            )

        private fun provideGetWorkoutsUseCase(workoutsRepository: WorkoutsRepository) =
            GetWorkoutsUseCase(
                workoutsRepository = workoutsRepository
            )

        private fun provideInsertWorkoutUseCase(workoutsRepository: WorkoutsRepository) =
            InsertWorkoutUseCase(
                workoutsRepository = workoutsRepository
            )

        private fun provideObserveWorkoutUseCase(workoutsRepository: WorkoutsRepository) =
            ObserveWorkoutUseCase(
                workoutsRepository = workoutsRepository
            )
    }
}