package bell.test.guidomia_challenge.ui.cars.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import bell.test.guidomia_challenge.R
import bell.test.guidomia_challenge.databinding.CardFilterBinding
import bell.test.guidomia_challenge.databinding.FragmentCarsHomeBinding
import bell.test.guidomia_challenge.databinding.HeaderBinding


class CarsFragment : Fragment() {

    private var _binding: FragmentCarsHomeBinding? = null
    private val binding get() = _binding!!

    private val includeHeaderBinding: HeaderBinding get() = _includeHeaderBinding!!
    private var _includeHeaderBinding: HeaderBinding? = null

    private val includeCardFilterBinding: CardFilterBinding get() = _includeCardFilterBinding!!
    private var _includeCardFilterBinding: CardFilterBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarsHomeBinding.inflate(inflater, container, false)
        _includeHeaderBinding = HeaderBinding.bind(binding.root)
        _includeCardFilterBinding = CardFilterBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    private fun initView() {
        includeHeaderBinding.apply {
            tvHeaderTitle.text = getString(R.string.header_title)
            tvHeaderDesc.text = getString(R.string.header_description)
        }


    }

}

