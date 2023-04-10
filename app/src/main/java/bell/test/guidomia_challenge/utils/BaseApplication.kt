package bell.test.guidomia_challenge.utils

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {
    companion object {
        lateinit var INSTANCE: BaseApplication
    }

    @Inject
    lateinit var sharedPrefsHelper: SharedPrefsHelper

    init {
        INSTANCE = this
    }
}