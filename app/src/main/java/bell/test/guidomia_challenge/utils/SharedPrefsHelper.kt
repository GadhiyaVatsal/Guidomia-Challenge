package bell.test.guidomia_challenge.utils

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsHelper @Inject constructor(
    var mSharedPreferences: SharedPreferences,
) {

    fun put(key: String, value: String){
        mSharedPreferences.edit().putString(key, value).apply()
    }

    operator fun get(key: String, defaultValue: String): String? {
        return mSharedPreferences.getString(key, defaultValue)
    }

    fun deleteSavedData(key: String) {
        mSharedPreferences.edit().remove(key).apply()
    }

    fun deleteAll() {
        mSharedPreferences.edit().clear().apply()
    }
}