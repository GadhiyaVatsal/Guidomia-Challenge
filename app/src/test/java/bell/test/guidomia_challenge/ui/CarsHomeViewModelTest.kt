package bell.test.guidomia_challenge.ui

import androidx.lifecycle.Observer
import bell.test.guidomia_challenge.ui.cars.fragment.CarsHomeViewModel
import bell.test.guidomia_challenge.ui.cars.fragment.entity.CarEntity
import bell.test.guidomia_challenge.utils.Constants
import bell.test.guidomia_challenge.utils.SharedPrefsHelper
import bell.test.guidomia_challenge.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.io.InputStream


@RunWith(MockitoJUnitRunner::class)
class CarsHomeViewModelTest {

    @Mock
    private lateinit var sharedPrefsHelper: SharedPrefsHelper

    @Mock
    private lateinit var resources: ResourcesProvider

    @Mock
    private lateinit var viewInteractor: CarsHomeContract

    private lateinit var viewModel: CarsHomeViewModel

    @Before
    fun setup() {
        viewModel = CarsHomeViewModel(sharedPrefsHelper, resources).apply {
            viewInteractor = this@CarsHomeViewModelTest.viewInteractor
        }
    }

    @Test
    fun `test fetchData success`() {
        val jsonCarsData = """
            [{
            	"consList": ["Bad direction"],
            	"customerPrice": 120000.0,
            	"make": "Land Rover",
            	"marketPrice": 125000.0,
            	"model": "Range Rover",
            	"prosList": ["You can go everywhere", "Good sound system"],
            	"rating": 3
            }, {
            	"consList": ["Sometime explode"],
            	"customerPrice": 220000.0,
            	"make": "Alpine",
            	"marketPrice": 225000.0,
            	"model": "Roadster",
            	"prosList": ["This car is so fast", "Jame Bond would love to steal that car", "", ""],
            	"rating": 4
            }]
        """.trimIndent()
        val carEntityList = arrayListOf<CarEntity>()
        val mockInputStream = mock(InputStream::class.java)
        val observer = mock(Observer::class.java) as Observer<ArrayList<CarEntity>>
        viewModel.carEntityData.observeForever(observer)

        `when`(sharedPrefsHelper[Constants.CARS_DATA_KEY, ""]).thenReturn(jsonCarsData)
        `when`(resources.getRawResource(R.raw.car_list)).thenReturn(mockInputStream)

        viewModel.fetchData()

        verify(viewInteractor).showLoading()
        verify(viewInteractor).hideLoading()
        verify(observer).onChanged(carEntityList)
    }

    @Test
    fun `test fetchData local file read error`() {
        val observer = mock(Observer::class.java) as Observer<ArrayList<CarEntity>>
        viewModel.carEntityData.observeForever(observer)

        `when`(sharedPrefsHelper[Constants.CARS_DATA_KEY, ""]).thenReturn("")
        `when`(resources.getRawResource(R.raw.car_list)).thenThrow(IOException())

        viewModel.fetchData()

        verify(viewInteractor).showLoading()
        verify(viewInteractor).hideLoading()
        verify(viewInteractor).showError(anyString())
    }

    @Test
    fun `test fetchData unknown error`() {

        val mockInputStream = mock(InputStream::class.java)
        val jsonCarsData = """
            [{
            	"consList": ["Bad direction"],
            	"customerPrice": 120000.0,
            	"make": "Land Rover",
            	"marketPrice": 125000.0,
            	"model": "Range Rover",
            	"prosList": ["You can go everywhere", "Good sound system"],
            	"rating": 3
            }, {
            	"consList": ["Sometime explode"],
            	"customerPrice": 220000.0,
            	"make": "Alpine",
            	"marketPrice": 225000.0,
            	"model": "Roadster",
            	"prosList": ["This car is so fast", "Jame Bond would love to steal that car", "", ""],
            	"rating: 4
            }]
        """.trimIndent()
        val observer = mock(Observer::class.java) as Observer<ArrayList<CarEntity>>
        viewModel.carEntityData.observeForever(observer)

        `when`(sharedPrefsHelper[Constants.CARS_DATA_KEY, ""]).thenReturn(jsonCarsData)
        `when`(resources.getRawResource(R.raw.car_list)).thenReturn(mockInputStream)

        viewModel.fetchData()

        verify(viewInteractor).showLoading()
        verify(viewInteractor).hideLoading()
        verify(viewInteractor).showError(anyString())
    }
}