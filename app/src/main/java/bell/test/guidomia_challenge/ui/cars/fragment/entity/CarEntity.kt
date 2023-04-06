package bell.test.guidomia_challenge.ui.cars.fragment.entity

import bell.test.guidomia_challenge.R
import com.google.gson.annotations.SerializedName

class CarEntity(
   @field:SerializedName("make")
   val make: String?,
   @field:SerializedName("model")
   val model: String?,
   @field:SerializedName("marketPrice")
   val marketPrice: Double?,
   @field:SerializedName("rating")
   val rating: Int?,
   @field:SerializedName("prosList")
   val prosList: List<String?>?,
   @field:SerializedName("consList")
   val consList: List<String?>?,
   @field:SerializedName("expanded")
   var expanded: Boolean = false,
   @field:SerializedName("image")
   var image: Int? = null
) {
   fun setImage() {
      when (this.model) {
         "GLE coupe" -> image = R.drawable.mercedez_benz_glc
         "3300i" -> image = R.drawable.bmw_330i
         "Roadster" -> image = R.drawable.alpine_roadster
         "Range Rover" -> image = R.drawable.range_rover
      }
   }
}