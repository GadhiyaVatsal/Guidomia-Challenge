package bell.test.guidomia_challenge.ui.cars.fragment

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import bell.test.guidomia_challenge.R
import bell.test.guidomia_challenge.ui.cars.fragment.entity.CarEntity
import bell.test.guidomia_challenge.utils.BaseViewModel
import bell.test.guidomia_challenge.utils.Constants
import bell.test.guidomia_challenge.utils.Constants.TAG_CARS_VIEW_MODEL
import bell.test.guidomia_challenge.utils.SharedPrefsHelper
import bell.test.guidomia_challenge.utils.di.ResourcesProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.reflect.Type
import javax.inject.Inject


@HiltViewModel
class CarsHomeViewModel @Inject constructor(
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val resourcesProvider: ResourcesProvider
) : BaseViewModel<CarsHomeContract>() {

    var carEntityData = MutableLiveData<ArrayList<CarEntity>>()
    var makeFilterData = MutableLiveData<ArrayList<String>>()
    var modelFilterData = MutableLiveData<ArrayList<String>>()

    private val gson = Gson()

    private var data = ArrayList<CarEntity>()
    private var currentList = ArrayList<CarEntity>()

    fun bindView() {
        viewInteractor.setUpView()
        viewInteractor.setUpObserver()
        viewInteractor.setUpAdapter()
    }

    fun fetchData() {
        viewModelScope.launch {
            viewInteractor.showLoading()
            try {
                val jsonCarsData = getData()
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
                currentList = data
                buildFilterData()
                viewInteractor.hideLoading()
                Log.d(TAG_CARS_VIEW_MODEL, "fetchData: Retrieved Car data $allCars")
            } catch (e: IOException) {
                viewInteractor.hideLoading()
                viewInteractor.showError("Error reading local file: ${e.message}")
                Log.e(TAG_CARS_VIEW_MODEL, "fetchData: Error reading local file: ${e.message}")
            } catch (e: Exception) {
                viewInteractor.hideLoading()
                viewInteractor.showError("Unknown Error Message: ${e.message}")
                Log.e(TAG_CARS_VIEW_MODEL, "fetchData: Unknown Error Message ${e.message}")
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
                if (it.make == make || it.model == model) filterData.add(it)
            }
        }

        currentList = filterData
        expandContainer(0, true)
    }

    fun expandContainer(position: Int, isFirst: Boolean = false) {
        if(isFirst) currentList[position].expanded = true
        else currentList[position].expanded =
            !currentList[position].expanded

        currentList.map {
            if (currentList.indexOf(it) != position) {
                it.expanded = false
            }
        }

        carEntityData.postValue(currentList)
    }


    private fun getData(): String {
        var jsonResponse = sharedPrefsHelper[Constants.CARS_DATA_KEY, ""]
        if (jsonResponse.isNullOrBlank()) {
            jsonResponse = resourcesProvider.getRawResource(R.raw.car_list)
                .bufferedReader().use { it.readText() }
            sharedPrefsHelper.put(Constants.CARS_DATA_KEY, jsonResponse)
        }
        return jsonResponse
    }
}