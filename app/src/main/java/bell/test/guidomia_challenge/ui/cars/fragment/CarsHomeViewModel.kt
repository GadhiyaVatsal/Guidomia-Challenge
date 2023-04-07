package bell.test.guidomia_challenge.ui.cars.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bell.test.guidomia_challenge.ui.cars.fragment.entity.CarEntity
import bell.test.guidomia_challenge.ui.cars.repository.CarRepository
import bell.test.guidomia_challenge.utils.BaseViewModel
import bell.test.guidomia_challenge.utils.Constants
import bell.test.guidomia_challenge.utils.helper.FunctionHelper
import bell.test.guidomia_challenge.utils.helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CarsHomeViewModel @Inject constructor(
    private val carRepository: CarRepository,
) : BaseViewModel() {

    private var _carEntityData = MutableLiveData<ArrayList<CarEntity>>()
    val carEntityData: LiveData<ArrayList<CarEntity>>
        get() = _carEntityData

    private var _makeFilterData = MutableLiveData<ArrayList<String>>()
    val makeFilterData : LiveData<ArrayList<String>>
        get() = _makeFilterData

    private var _modelFilterData = MutableLiveData<ArrayList<String>>()
    val modelFilterData: LiveData<ArrayList<String>>
    get() = _modelFilterData

    private var data = ArrayList<CarEntity>()
    private var currentList = ArrayList<CarEntity>()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    init {
        fetchData()
    }
    fun fetchData() {
        carRepository.getCars().observeForever { resource ->
            when(resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let {
                        data  = ArrayList(it)
                        currentList = ArrayList(it)
                        buildFilterData()
                        _carEntityData.postValue(currentList)

                        loading.value = false
                    }
                }
                Resource.Status.ERROR -> {
                    loading.value = false
                    resource.message?.let { errorMessage.value = it }
                }
                Resource.Status.LOADING -> {
                    loading.value = true
                }
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
        _makeFilterData.postValue(makeFilter)
        _modelFilterData.postValue(modelFilter)
    }
    fun filter(make: String, model: String) {
        carRepository.filter(make, model).observeForever { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { carList ->
                        currentList = ArrayList(carList)
                        expandContainer(0, true)

                        loading.value = false
                    }
                }
                Resource.Status.LOADING -> {
                    loading.value = true
                }
                Resource.Status.ERROR -> {
                    loading.value = false
                    resource.message?.let { errorMessage.value = it }
                }
            }
        }
    }
    fun expandContainer(position: Int, isFirst: Boolean = false) {
        _carEntityData.postValue(FunctionHelper.expandContainerHelper(position, isFirst, currentList))
    }
}