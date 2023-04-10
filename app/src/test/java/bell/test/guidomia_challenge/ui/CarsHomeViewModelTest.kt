package bell.test.guidomia_challenge.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import bell.test.guidomia_challenge.ui.cars.fragment.CarsHomeViewModel
import bell.test.guidomia_challenge.ui.cars.fragment.entity.CarEntity
import bell.test.guidomia_challenge.ui.cars.repository.ICarRepository
import bell.test.guidomia_challenge.util.Helper
import bell.test.guidomia_challenge.utils.helper.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
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

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    lateinit var carRepository: ICarRepository
    private val viewModel by lazy { CarsHomeViewModel(carRepository) }

    @Before
    fun setUp() {
        carRepository = mock(ICarRepository::class.java)
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

    @Test
    fun `filter cars data success`() {
        val make = "BMW"
        val model = "3300i"
        val expectedFilteredData = getCarsJsonData().filter { it.make == make && it.model == model }
        val liveData = MutableLiveData<Resource<List<CarEntity>>>()
        liveData.value = Resource.success(expectedFilteredData)
        `when`(carRepository.filter(make, model)).thenReturn(liveData)
        val dataObserver: Observer<ArrayList<CarEntity>> = mock(Observer::class.java) as Observer<ArrayList<CarEntity>>
        viewModel.carEntityData.observeForever(dataObserver)

        viewModel.filter(make, model)

        assertEquals(expectedFilteredData, viewModel.carEntityData.value)
        verify(carRepository).filter(make, model)
    }


    private fun getCarsJsonData(): ArrayList<CarEntity> {
        return Gson().fromJson(
            Helper.readFromFile("car_list.json"),
            object: TypeToken<ArrayList<CarEntity>>() {}.type
        )
    }
}