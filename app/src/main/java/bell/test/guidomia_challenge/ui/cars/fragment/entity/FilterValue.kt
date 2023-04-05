package bell.test.guidomia_challenge.ui.cars.fragment.entity

import bell.test.guidomia_challenge.utils.Constants

data class FilterValue(
    var makeValue: String = Constants.ANY_MAKE,
    var modelValue: String = Constants.ANY_MODEL
)