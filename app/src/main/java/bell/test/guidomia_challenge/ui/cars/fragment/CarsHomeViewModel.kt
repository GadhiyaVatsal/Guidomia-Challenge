package bell.test.guidomia_challenge.ui.cars.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
    private val sharedPrefsHelper: SharedPrefsHelper,
) : BaseViewModel<CarsHomeContract>() {

    var carEntityData = MutableLiveData<ArrayList<CarEntity>>()

    var makeFilterData = MutableLiveData<ArrayList<String>>()
    var modelFilterData = MutableLiveData<ArrayList<String>>()

    private var filterValue = FilterValue()
    val gson = Gson()

    private var data = ArrayList<CarEntity>()

    fun bindView() {
        viewInteractor.setUpView()
        viewInteractor.setUpObserver()
        viewInteractor.setUpAdapter()
    }

    fun fetchData() {
        viewModelScope.launch {
            viewInteractor.showLoading()
            try {
                val jsonCarsData = sharedPrefsHelper.getData()
                Log.d(TAG, "fetchData: $jsonCarsData")
                val listType: Type = object : TypeToken<ArrayList<CarEntity>>() {}.type
                val carEntityList: ArrayList<CarEntity> = gson.fromJson(jsonCarsData, listType)
                val allCars = carEntityList.map {
                    it.setImage()
                    it
                }
                allCars[0].expanded = true
                allCars.let {
                    data.addAll(it)
                }
                carEntityData.postValue(data)
                buildFilterData()
                viewInteractor.hideLoading()
                Log.d(TAG, "fetchData Mutable: ${carEntityData.value}")
            } catch (e: Exception) {
                viewInteractor.hideLoading()
                Log.e(TAG, "Unknown Error Message: ${e.message}")
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

        data.forEach { carEntityData ->
            carEntityData.make?.let { it -> makeFilter.add(it) }
            carEntityData.model?.let { it -> modelFilter.add(it) }
        }
        makeFilterData.postValue(makeFilter)
        modelFilterData.postValue(modelFilter)
    }

    fun filter(make: String, model: String) {
        var filterData = ArrayList<CarEntity>()
        when {
            make == Constants.ANY_MAKE && model == Constants.ANY_MODEL -> filterData =
                data
            make == Constants.ANY_MAKE -> data.forEach {
                if (model == it.model) filterData.add(it)
            }
            model == Constants.ANY_MODEL -> data.forEach {
                if (make == it.make) filterData.add(it)
            }
            else -> data.forEach {
                if (it.make == make && it.model == model) filterData.add(it)
            }
        }

//        expandContainer(0, filterData)
        filterData[0].expanded = true
        filterData.map {
            if (filterData.indexOf(it) != 0) {
                it.expanded = false
            }
        }

        carEntityData.postValue(filterData)
    }

    fun expandContainer(position: Int) {
        data[position].expanded =
            !data[position].expanded
        data.map {
            if (data.indexOf(it) != position) {
                it.expanded = false
            }
        }
        carEntityData.postValue(data)
    }

}