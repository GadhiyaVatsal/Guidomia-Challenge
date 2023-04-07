package bell.test.guidomia_challenge.utils.repository

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import bell.test.guidomia_challenge.R
import bell.test.guidomia_challenge.ui.cars.fragment.entity.CarEntity
import bell.test.guidomia_challenge.ui.cars.repository.CarRepository
import bell.test.guidomia_challenge.utils.Constants
import bell.test.guidomia_challenge.utils.SharedPrefsHelper
import bell.test.guidomia_challenge.utils.helper.GsonUtil
import bell.test.guidomia_challenge.utils.helper.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import javax.inject.Inject

class ApiServiceRepository @Inject constructor(
    private val sharedPrefsHelper: SharedPrefsHelper,
    @ApplicationContext private val context: Context
) : CarRepository {
    private val resources: Resources = context.resources

    override fun getCars(): LiveData<Resource<List<CarEntity>>> = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val carData = getCarFromJson()
            carData[0].expanded = true
            emit(Resource.success(carData))
            Log.d(Constants.TAG_API_REPOSITORY, "getCars: Total cars are ${carData.size} ")
        } catch (e: IOException) {
            emit(Resource.error("Error reading json file"))
            Log.e(Constants.TAG_API_REPOSITORY, "Error reading json file")
        } catch (e: Exception) {
            emit(Resource.error("Unknown error occurred"))
            Log.e(Constants.TAG_API_REPOSITORY, "Unknown error occurred")
        }
    }

    override fun filter(make: String, model: String): LiveData<Resource<List<CarEntity>>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            try {
                val carData = getCarFromJson()
                val filteredData = carData.filter { carEntity ->
                    (make == Constants.ANY_MAKE || carEntity.make == make) &&
                            (model == Constants.ANY_MODEL || carEntity.model == model) ||
                            (carEntity.model == model || carEntity.make == make)
                }
                emit(Resource.success(filteredData))
            } catch (e: Exception) {
                emit(Resource.error("Unknown error occurred"))
                Log.e(Constants.TAG_API_REPOSITORY, "Unknown error occurred")
            }
        }

    private fun getCarFromJson(): List<CarEntity> {
        Log.d(Constants.TAG_API_REPOSITORY, "getCarFromJson: ")
        var sharedPrefCarData = sharedPrefsHelper[Constants.CARS_DATA_KEY, ""]

        if (sharedPrefCarData.isNullOrEmpty()) {
            sharedPrefCarData =
                resources.openRawResource(R.raw.car_list).bufferedReader().use { it.readText() }
            sharedPrefsHelper.put(Constants.CARS_DATA_KEY, sharedPrefCarData)
        }
        return GsonUtil.convertJsonToDataList(sharedPrefCarData)
    }
}