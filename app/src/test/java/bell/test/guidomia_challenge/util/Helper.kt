package bell.test.guidomia_challenge.util

import java.io.IOException
import java.io.InputStream

object Helper {
    @Throws(IOException::class)
    fun readFromFile(filename: String?): String {
        val `is`: InputStream? = javaClass.classLoader?.getResourceAsStream(filename)
        val stringBuilder = StringBuilder()
        var i: Int
        val b = ByteArray(4096)
        while (`is`?.read(b).also { i = it!! } != -1) {
            stringBuilder.append(String(b, 0, i))
        }
        return stringBuilder.toString()
    }
}