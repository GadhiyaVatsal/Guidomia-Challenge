package bell.test.guidomia_challenge.utils

import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    override fun onCleared() {
        super.onCleared()
    }
}