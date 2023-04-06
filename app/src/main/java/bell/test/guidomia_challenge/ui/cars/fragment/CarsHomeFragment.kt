package bell.test.guidomia_challenge.ui.cars.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import bell.test.guidomia_challenge.R
import bell.test.guidomia_challenge.databinding.CarHeaderBinding
import bell.test.guidomia_challenge.databinding.CardFilterBinding
import bell.test.guidomia_challenge.databinding.FragmentCarsHomeBinding
import bell.test.guidomia_challenge.ui.cars.fragment.adapter.CarListAdapter
import bell.test.guidomia_challenge.ui.cars.fragment.entity.CarEntity
import bell.test.guidomia_challenge.ui.cars.fragment.entity.FilterValue
import bell.test.guidomia_challenge.utils.BaseFragment
import bell.test.guidomia_challenge.utils.Constants
import bell.test.guidomia_challenge.utils.helper.CustomDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarsHomeFragment : BaseFragment<FragmentCarsHomeBinding, CarsHomeViewModel>(),
    CarsHomeContract {

    private lateinit var carAdapter: CarListAdapter
    private lateinit var makeSpinnerAdapter: ArrayAdapter<String>
    private lateinit var modelSpinnerAdapter: ArrayAdapter<String>

    private val includeCarHeaderBinding: CarHeaderBinding get() = _includeCarHeaderBinding!!
    private var _includeCarHeaderBinding: CarHeaderBinding? = null

    private val includeCardFilterBinding: CardFilterBinding get() = _includeCardFilterBinding!!
    private var _includeCardFilterBinding: CardFilterBinding? = null

    private var filterValue = FilterValue()
    var data: MutableList<CarEntity>? = arrayListOf()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewInteractor = this
        viewModel.fetchData()
        carAdapter = CarListAdapter(viewModel)
        _includeCarHeaderBinding = CarHeaderBinding.bind(binding.root)
        _includeCardFilterBinding = CardFilterBinding.bind(binding.root)
        _includeCarHeaderBinding!!.ivHeader.setRenderEffect(
            RenderEffect.createBlurEffect(
                2F,
                2F,
                Shader.TileMode.MIRROR
            )
        )
        viewModel.bindView()
    }

    override fun setUpView() {
        includeCarHeaderBinding.apply {
            tvHeaderTitle.text = getString(R.string.header_title)
            tvHeaderDescription.text = getString(R.string.header_description)
        }
    }

    override fun setUpAdapter() {
        makeSpinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item)
        _includeCardFilterBinding?.apply {
            makeSpinner.adapter = makeSpinnerAdapter
            makeSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        position: Int,
                        id: Long
                    ) {
                        makeSpinnerAdapter.getItem(position)?.let {
                            viewModel.filter(
                                make = it,
                                model = modelSpinner.selectedItem.toString()
                            )
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }
                }
        }

        modelSpinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item)
        _includeCardFilterBinding?.apply {
            modelSpinner.adapter = modelSpinnerAdapter
            modelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    id: Long
                ) {
                    modelSpinnerAdapter.getItem(position)?.let {
                        viewModel.filter(make = makeSpinner.selectedItem.toString(), model = it)
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }


        binding.rvCars.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            isNestedScrollingEnabled = false
            val dividerDrawable = ContextCompat.getDrawable(context, R.drawable.bg_divider)
            val padding = resources.getDimensionPixelSize(R.dimen.margin_12)
            val customDividerItemDecoration =
                dividerDrawable?.let { CustomDividerItemDecoration(it, padding) }

            if (customDividerItemDecoration != null) {
                addItemDecoration(customDividerItemDecoration)
            }
            this.adapter = carAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setUpObserver() {

        viewModel.makeFilterData.observe(this, Observer {
            makeSpinnerAdapter.addAll(it)
        })

        viewModel.modelFilterData.observe(this, Observer {
            modelSpinnerAdapter.addAll(it)
        })

        viewModel.carEntityData.observe(this, Observer {
            binding.rvCars.recycledViewPool.clear()
            carAdapter.updateList(it)
            carAdapter.notifyDataSetChanged()
        })
    }

    override fun showLoading() {

        binding.progressBar.visibility = View.VISIBLE
        binding.rvCars.visibility = View.GONE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.rvCars.visibility = View.VISIBLE
    }

    override fun showError(msg: String) {
        TODO("Not yet implemented")
    }

    override fun showErrorRecyclerView(msg: String, context: Context) {
        TODO("Not yet implemented")
    }

    override val viewModel: CarsHomeViewModel by viewModels()

    companion object {
        val TAG: String = Constants.TAG_CARS_FRAGMENT
    }

    override fun setBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCarsHomeBinding {
        return FragmentCarsHomeBinding.inflate(layoutInflater)
    }

}