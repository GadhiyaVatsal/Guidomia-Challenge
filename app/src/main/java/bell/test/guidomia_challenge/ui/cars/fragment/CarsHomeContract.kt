package bell.test.guidomia_challenge.ui.cars.fragment

import android.content.Context
import bell.test.guidomia_challenge.utils.ViewInteractor

interface CarsHomeContract: ViewInteractor {

    fun setUpView()
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showErrorRecyclerView(msg: String, context: Context)
    fun setUpObserver()
    fun setUpAdapter()
}