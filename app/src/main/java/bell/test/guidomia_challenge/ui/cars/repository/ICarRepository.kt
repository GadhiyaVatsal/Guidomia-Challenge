package bell.test.guidomia_challenge.ui.cars.repository

import androidx.lifecycle.LiveData
import bell.test.guidomia_challenge.ui.cars.fragment.entity.CarEntity
import bell.test.guidomia_challenge.utils.helper.Resource

interface ICarRepository {
    fun getCars(): LiveData<Resource<List<CarEntity>>>
    fun filter(make: String, model: String): LiveData<Resource<List<CarEntity>>>
}