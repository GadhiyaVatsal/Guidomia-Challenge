package bell.test.guidomia_challenge.utils.di

import bell.test.guidomia_challenge.ui.cars.repository.CarRepository
import bell.test.guidomia_challenge.ui.cars.repository.ICarRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CarRepositoryModule {
    @Binds
    abstract fun bindCarRepository(repo: CarRepository): ICarRepository
}