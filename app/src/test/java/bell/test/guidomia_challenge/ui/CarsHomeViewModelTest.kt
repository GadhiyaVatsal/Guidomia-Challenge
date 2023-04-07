package bell.test.guidomia_challenge.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import bell.test.guidomia_challenge.ui.cars.fragment.CarsHomeViewModel
import bell.test.guidomia_challenge.ui.cars.fragment.entity.CarEntity
import bell.test.guidomia_challenge.ui.cars.repository.CarRepository
import bell.test.guidomia_challenge.util.Helper
import bell.test.guidomia_challenge.utils.helper.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CarsHomeViewModelTest {

    @Mock
    lateinit var carRepository: CarRepository
    private lateinit var viewModel: CarsHomeViewModel

    @Before
    fun setUp() {
        carRepository = mock(CarRepository::class.java)
        viewModel = CarsHomeViewModel(carRepository)
    }

    @Test
    fun `fetchData cars data success`() {
        val expectedData = getCarsJsonData()
        val liveData = MutableLiveData<Resource<List<CarEntity>>>()
        liveData.value = Resource.success(expectedData)
        `when`(carRepository.getCars()).thenReturn(liveData)
        val dataObserver: Observer<ArrayList<CarEntity>> = mock(Observer::class.java) as Observer<ArrayList<CarEntity>>
        viewModel.carEntityData.observeForever(dataObserver)
        viewModel.fetchData()
        assertEquals(expectedData, viewModel.carEntityData.value)
        verify(carRepository).getCars()
    }

    // Ad

    /*@Test
    fun `fetchData error`() {
        val viewModel = CarsHomeViewModel(carRepository)
        val errorMessage = "Error loading data"
        `when`(carRepository.getCars()).thenReturn(liveData { Resource.error(errorMessage, "") })

        viewModel.fetchData()

        verify(carRepository).getCars()
    }*/

    fun getCarsJsonData(): ArrayList<CarEntity> {
        return Gson().fromJson(
            Helper.readFromFile("car_list.json"),
            object: TypeToken<ArrayList<CarEntity>>() {}.type
        )
    }
}