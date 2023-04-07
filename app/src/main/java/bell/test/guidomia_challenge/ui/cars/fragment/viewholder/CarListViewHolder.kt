package bell.test.guidomia_challenge.ui.cars.fragment.viewholder

import android.os.Build
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import android.util.Log
import android.view.ViewGroup.MarginLayoutParams
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import bell.test.guidomia_challenge.R
import bell.test.guidomia_challenge.databinding.CarItemLayoutBinding
import bell.test.guidomia_challenge.ui.cars.fragment.CarsHomeViewModel
import bell.test.guidomia_challenge.ui.cars.fragment.entity.CarEntity
import bell.test.guidomia_challenge.utils.helper.FunctionHelper.getPriceToDisplay
import javax.inject.Inject

class CarListViewHolder @Inject constructor(
    private val binding: CarItemLayoutBinding,
) : RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.P)
    fun setData(carEntity: CarEntity, position: Int, viewModel: CarsHomeViewModel) {
        with(carEntity) {
            binding.tvPrice.text = marketPrice?.toInt()?.getPriceToDisplay() ?: "0"
            binding.tvCarName.text = model
            binding.ratingBar.rating = rating?.toFloat() ?: 5.0.toFloat()
            if (model != null) {
                getImage(model).let { image ->
                    image?.let {
                        binding.ivCar.setImageResource(it)
                    }
                }
            }
            binding.carContainer.setOnClickListener {
                viewModel.expandContainer(position)
            }
            if (expanded) {
                clearData()
                buildProsString(carEntity)?.let { pros ->
                    Log.d("CarListViewHolder", "setData: In $pros")
                    binding.tvProsTitle.isVisible = true
                    binding.tvProsList.isVisible = true
                    binding.tvProsList.text = pros
                }
                buildConsString(carEntity)?.let { cons ->
                    if (prosList.isNullOrEmpty()) {
                        Log.d("CarListViewHolder", "Empty Pros")
                        val topMargin =
                            binding.root.context.resources.getDimensionPixelSize(R.dimen.margin_20)

                        val layoutParam = binding.tvConsTitle.layoutParams as? MarginLayoutParams
                        layoutParam?.let {
                            it.topMargin = topMargin
                            binding.tvConsTitle.layoutParams = it
                        }
                    }
                    binding.tvConsTitle.isVisible = true
                    binding.tvConsList.isVisible = true
                    binding.tvConsList.text = cons
                }
            } else {
                binding.tvProsTitle.isVisible = false
                binding.tvProsList.isVisible = false
                binding.tvConsTitle.isVisible = false
                binding.tvConsList.isVisible = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun buildProsString(data: CarEntity): SpannableString? {
        if (data.prosList.isNullOrEmpty()) return null
        return createSpannableStringList(data.prosList)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun buildConsString(data: CarEntity): SpannableString? {
        if (data.consList.isNullOrEmpty()) return null
        return createSpannableStringList(data.consList)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun createSpannableStringList(strList: List<String?>): SpannableString {
        var builder = SpannableStringBuilder()
        strList.forEachIndexed { index, str ->
            if (!str.isNullOrEmpty()) {
                val string = SpannableString(str)
                string.setSpan(
                    BulletSpan(40, binding.root.context.getColor(R.color.orange), 10),
                    0,
                    string.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                builder.append(string)
                if (index < strList.lastIndex) builder.append("\n")
            }
        }
        return SpannableString(builder)
    }

    private fun getImage(model: String): Int? {
        return when (model) {
            "GLE coupe" -> R.drawable.mercedez_benz_glc
            "3300i" -> R.drawable.bmw_330i
            "Roadster" -> R.drawable.alpine_roadster
            "Range Rover" -> R.drawable.range_rover
            else -> null
        }
    }

    private fun clearData() {
        binding.tvProsTitle.isVisible = false
        binding.tvProsList.isVisible = false
        binding.tvConsTitle.isVisible = false
        binding.tvConsList.isVisible = false
    }

}