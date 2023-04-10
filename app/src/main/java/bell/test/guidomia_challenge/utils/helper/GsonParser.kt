package bell.test.guidomia_challenge.utils.helper

import android.util.Log
import bell.test.guidomia_challenge.ui.cars.fragment.entity.CarEntity
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class GsonParser {

    fun <T> formJson(json: String, type: Type): T {
        try {
            val res = Gson().fromJson<T>(json, type)
            Log.d("Gson_Parser", "formJson: $res")
            return res
        } catch (e: Exception) {
            throw JsonParseException(e.toString())
        }
    }
}