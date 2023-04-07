package bell.test.guidomia_challenge.utils.helper

import bell.test.guidomia_challenge.R
import bell.test.guidomia_challenge.ui.cars.fragment.entity.CarEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.lang.reflect.Type

object GsonUtil {

    fun convertJsonToDataList(jsonData: String): ArrayList<CarEntity> {
        val listType: Type = object : TypeToken<ArrayList<CarEntity>>() {}.type
        return (Gson().fromJson(jsonData, listType) ?: ArrayList<CarEntity>())
    }

}