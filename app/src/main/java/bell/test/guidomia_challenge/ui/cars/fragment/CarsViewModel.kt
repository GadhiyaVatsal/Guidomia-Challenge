package bell.test.guidomia_challenge.ui.cars.fragment

import androidx.lifecycle.MutableLiveData
import bell.test.guidomia_challenge.ui.cars.fragment.entity.Car
import bell.test.guidomia_challenge.utils.BaseViewModel
import bell.test.guidomia_challenge.utils.Constants
import bell.test.guidomia_challenge.utils.SharedPrefsHelper

class CarsViewModel  constructor(
    var sharedPrefsHelper: SharedPrefsHelper
): BaseViewModel<CarsContracts>() {

    var carData = MutableLiveData<ArrayList<Car>>()

    var makeFilterData = MutableLiveData<ArrayList<String>>()
    var modelFilterData = MutableLiveData<ArrayList<String>>()


    private var currentMakeFilter: String  = Constants.ANY_MAKE
    private var currentModelFilter: String = Constants.ANY_MODEL
}