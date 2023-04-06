package bell.test.guidomia_challenge.utils.di

import android.content.Context
import androidx.annotation.RawRes
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourcesProvider @Inject constructor(
    @ApplicationContext private val context:Context
) {

    fun getRawResource(@RawRes rawResId: Int): InputStream {
        return context.resources.openRawResource(rawResId)
    }
}