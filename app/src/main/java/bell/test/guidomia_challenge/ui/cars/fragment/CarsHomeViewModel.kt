package bell.test.guidomia_challenge.ui.cars.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import bell.test.guidomia_challenge.R
import bell.test.guidomia_challenge.ui.cars.fragment.entity.CarEntity
import bell.test.guidomia_challenge.ui.cars.fragment.entity.FilterValue
import bell.test.guidomia_challenge.utils.BaseViewModel
import bell.test.guidomia_challenge.utils.Constants
import bell.test.guidomia_challenge.utils.SharedPrefsHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject

private const val TAG = "CarsHomeViewModel"

@HiltViewModel
class CarsHomeViewModel @Inject constructor(
    var sharedPrefsHelper: SharedPrefsHelper
): BaseViewModel<CarsHomeContract>() {

    var carEntityData = MutableLiveData<ArrayList<CarEntity>>()

    var makeFilterData = MutableLiveData<ArrayList<String>>()
    var modelFilterData = MutableLiveData<ArrayList<String>>()


    private var filterValue = FilterValue()
    val gson = Gson()

    fun fetchData() {
        viewModelScope.launch {
            viewInteractor.showLoading()
            try {
                val jsonCarsData = getData()
                val listType: Type = object : TypeToken<List<CarEntity>>() {}.type
                val carEntityList: ArrayList<CarEntity> = gson.fromJson(jsonCarsData, listType)
                carEntityList.let { carEntityData.value?.addAll(it) }
                buildFilterData()
                Log.d(TAG, "fetchData: ${carEntityData.value}")
            }catch (e: Exception){
                Log.e(TAG, "fetchData: ${e.message}")
            }
        }
    }

    private fun buildFilterData() {
        val makeFilter = ArrayList<String>().apply {
            add(Constants.ANY_MAKE)
        }
        val modelFilter = ArrayList<String>().apply {
            add(Constants.ANY_MODEL)
        }

        carEntityData.value?.forEach { carEntityData ->
            carEntityData.make?.let { it -> makeFilter.add(it) }
            carEntityData.model?.let { it -> modelFilter.add(it) }
        }
        makeFilterData.postValue(makeFilter)
        modelFilterData.postValue(modelFilter)
    }

    fun filter(make: String, model: String) {
        var filterData = ArrayList<CarEntity>()
        when {
            make == Constants.ANY_MAKE && model == Constants.ANY_MODEL -> filterData = carEntityData.value!!
            make == Constants.ANY_MAKE -> carEntityData.value?.forEach { if (model == it.model) filterData.add(it) }
            model == Constants.ANY_MODEL -> carEntityData.value?.forEach { if (make == it.make) filterData.add(it) }
            else -> carEntityData.value?.forEach { if(it.make == make && it.model == model) filterData.add(it) }
        }
    }

    private fun getData(): String {
        var jsonResponse = sharedPrefsHelper.get(Constants.CARS_DATA_KEY, "")
        if(jsonResponse.isNullOrBlank()){
            jsonResponse = viewInteractor.getContext().resources.openRawResource(R.raw.car_list)
                .bufferedReader().use { it.readText() }
            sharedPrefsHelper.put(Constants.CARS_DATA_KEY, jsonResponse)
        }
        return jsonResponse
    }
}