package bell.test.guidomia_challenge.ui.cars.fragment.di

import androidx.fragment.app.Fragment
import bell.test.guidomia_challenge.ui.cars.fragment.CarsHomeFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class CarsModule {
    @Provides
    fun bindFragment(fragment: Fragment): CarsHomeFragment{
        return fragment as CarsHomeFragment
    }
    @Provides
    fun view(fragment: CarsHomeFragment) : CarsHomeFragment{
        return fragment
    }
}