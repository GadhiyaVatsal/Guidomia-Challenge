package bell.test.guidomia_challenge.ui.cars.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import bell.test.guidomia_challenge.databinding.FragmentCarsHomeBinding
import bell.test.guidomia_challenge.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarsHomeFragment : Fragment(), CarsHomeContract{

    private val viewModel: CarsHomeViewModel by viewModels()
//    private lateinit var mAdapter: CarListAdapter


    private var _binding: FragmentCarsHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarsHomeBinding.inflate(inflater, container, false)
//        _includeHeaderBinding = HeaderBinding.bind(binding.root)
//        _includeCardFilterBinding = CardFilterBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchData()
    }
    override fun setUpView() {
        TODO("Not yet implemented")
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showError(msg: String) {
        TODO("Not yet implemented")
    }

    override fun showErrorRecyclerView(msg: String, context: Context) {
        TODO("Not yet implemented")
    }

    override fun getContext(): Context {
        TODO("Not yet implemented")
    }
}