package bell.test.guidomia_challenge.ui.cars.fragment.entity

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
)