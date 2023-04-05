package bell.test.guidomia_challenge.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding, VM : ViewModel> : Fragment() {

    protected lateinit var binding: B

    protected abstract val viewModel: VM

    abstract fun setBinding(layoutInflater: LayoutInflater, container: ViewGroup?): B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = setBinding(layoutInflater, container)
        activity?.let {
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }
}