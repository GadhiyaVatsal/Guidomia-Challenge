package bell.test.guidomia_challenge.utils.di

import android.content.Context
import android.content.SharedPreferences
import bell.test.guidomia_challenge.utils.BaseApplication
import bell.test.guidomia_challenge.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideSharedPref(): SharedPreferences {
        return BaseApplication.INSTANCE.getSharedPreferences(Constants.SHARED_PREF_KEY, Context.MODE_PRIVATE)
    }
}