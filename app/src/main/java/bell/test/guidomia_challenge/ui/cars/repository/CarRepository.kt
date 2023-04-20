package bell.test.guidomia_challenge.ui.cars.repository

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import bell.test.guidomia_challenge.R
import bell.test.guidomia_challenge.ui.cars.fragment.entity.CarEntity
import bell.test.guidomia_challenge.utils.Constants
import bell.test.guidomia_challenge.utils.SharedPrefsHelper
import bell.test.guidomia_challenge.utils.helper.GsonParser
import bell.test.guidomia_challenge.utils.helper.Resource
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import java.lang.reflect.Type
import javax.inject.Inject

class CarRepository @Inject constructor(
    private val sharedPrefsHelper: SharedPrefsHelper,
    @ApplicationContext private val context: Context
) : ICarRepository {
    private val resources: Resources = context.resources

    override fun getCars(): LiveData<Resource<List<CarEntity>>> = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val carData = getCarFromJson()
            carData[0].expanded = true
            emit(Resource.success(carData))
            Log.d(Constants.TAG_CAR_REPOSITORY, "getCars: Total cars are $carData")
        } catch (e: IOException) {
            emit(Resource.error("Error reading json file"))
            Log.e(Constants.TAG_CAR_REPOSITORY, "Error reading json file")
        } catch (e: Exception) {
            emit(Resource.error("Unknown error occurred"))
            Log.e(Constants.TAG_CAR_REPOSITORY, "Unknown error occurred")
        }
    }

    override fun filter(make: String, model: String): LiveData<Resource<List<CarEntity>>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            try {
                val carData = getCarFromJson()
                // AND condition check either of one filter is ANY and check other filter if both filter are selected then check OR condition for both filters value
                val filteredData = carData.filter { carEntity ->
                    (make == Constants.ANY_MAKE || carEntity.make == make) &&
                            (model == Constants.ANY_MODEL || carEntity.model == model) ||
                            (carEntity.model == model || carEntity.make == make)
                }
                emit(Resource.success(filteredData))
            } catch (e: Exception) {
                emit(Resource.error("Unknown error occurred"))
                Log.e(Constants.TAG_CAR_REPOSITORY, "Unknown error occurred")
            }
        }

    private fun getCarFromJson(): List<CarEntity> {
        var sharedPrefCarData = sharedPrefsHelper.get(Constants.CARS_DATA_KEY, "")

        if (sharedPrefCarData.isNullOrEmpty()) {
            sharedPrefCarData =
                resources.openRawResource(R.raw.car_list).bufferedReader().use { it.readText() }
            sharedPrefsHelper.put(Constants.CARS_DATA_KEY, sharedPrefCarData)
        }
        val listType: Type = object : TypeToken<List<CarEntity>>() {}.type
        return GsonParser().formJson(sharedPrefCarData, listType)
    }
}