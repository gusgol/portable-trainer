package tech.gusgol.portabletrainer

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import androidx.room.RoomDatabase
import tech.gusgol.portabletrainer.data.workouts.DefaultWorkoutsRepository
import tech.gusgol.portabletrainer.data.workouts.WorkoutsDataSource
import tech.gusgol.portabletrainer.data.workouts.WorkoutsLocalDataSource
import tech.gusgol.portabletrainer.data.workouts.WorkoutsRepository
import tech.gusgol.portabletrainer.db.AppDatabase
import tech.gusgol.portabletrainer.domain.GetWorkoutsUseCase
import tech.gusgol.portabletrainer.domain.InsertWorkoutUseCase
import tech.gusgol.portabletrainer.ui.home.HomeViewModel
import tech.gusgol.portabletrainer.ui.workouts.create.CreateWorkoutViewModel

object ServiceLocator {

    private var db: AppDatabase? = null

    fun init(application: Application) {
        db = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "portable-trainer-db"
        ).build()
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
    }
}