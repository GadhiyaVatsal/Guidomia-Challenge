package bell.test.guidomia_challenge.ui.cars.fragment.entity

import bell.test.guidomia_challenge.R
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Car(
   @Json(name = "make") var make: String?,
   @Json(name = "model") var model: String?,
   @Json(name = "marketPrice") var marketPrice: Double?,
   @Json(name = "rating") var rating: Int?,
   @Json(name = "prosList") var prosList: List<String?>?,
   @Json(name = "consList") var consList: List<String?>?,
   @Json(name = "expanded") var expanded: Boolean = false,
   @Json(name = "image") var image: Int? = null
) {

    fun setImage() {
        when (model) {
            "GLE coupe" -> image = R.drawable.mercedez_benz_glc
            "3300i" -> image = R.drawable.bmw_330i
            "Roadster" -> image = R.drawable.alpine_roadster
            "Range Rover" -> image = R.drawable.range_rover
        }
    }
}