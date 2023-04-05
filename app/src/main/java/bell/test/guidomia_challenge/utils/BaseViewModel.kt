package bell.test.guidomia_challenge.utils

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<VI: ViewInteractor>: ViewModel() {


    lateinit var viewInteractor: VI


    override fun onCleared() {
        super.onCleared()
    }
}