package bell.test.guidomia_challenge.utils

import bell.test.guidomia_challenge.ui.cars.fragment.entity.Car
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

object Common {

    @ToJson
    fun arrayListToJson(list: ArrayList<Car>) : List<Car> = list

    @FromJson
    fun arrayListFromJson(list: List<Car>) : ArrayList<Car> = ArrayList(list)

    fun Int.getPriceToDisplay(): String {
        return when {
            this >= 1000000 -> "${this.div(1000000)}M"
            this >= 1000 -> "${this.div(1000)}k"
            else -> this.toString()
        }
    }


}