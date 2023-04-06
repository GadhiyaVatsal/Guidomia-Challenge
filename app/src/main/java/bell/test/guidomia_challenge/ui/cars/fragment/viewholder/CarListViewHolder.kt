package bell.test.guidomia_challenge.ui.cars.fragment.viewholder

import android.os.Build
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import bell.test.guidomia_challenge.R
import bell.test.guidomia_challenge.databinding.CarItemLayoutBinding
import bell.test.guidomia_challenge.ui.cars.fragment.CarsHomeViewModel
import bell.test.guidomia_challenge.ui.cars.fragment.entity.CarEntity
import bell.test.guidomia_challenge.utils.Common.getPriceToDisplay

class CarListViewHolder(
    private val binding: CarItemLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.P)
    fun setData(carEntity: CarEntity, position: Int, viewModel: CarsHomeViewModel) {
        val context = binding.root.context
        with(carEntity) {
            binding.tvPrice.text = marketPrice?.getPriceToDisplay() ?: "0"
            binding.tvCarName.text = model
            binding.ratingBar.rating = rating?.toFloat() ?: 5.0.toFloat()
            binding.ivCar.setImageResource(image!!)
            binding.containerCarSubDetails.visibility = if(expanded) View.VISIBLE else View.GONE
            binding.carContainer.setOnClickListener {
                viewModel.expandContainer(position)
            }
            if(expanded){
                buildProsString(carEntity)?.let { pros ->
                    binding.tvProsTitle.isVisible = true
                    binding.tvProsList.isVisible = true
                    binding.tvProsList.text = pros
                }
                buildConsString(carEntity)?.let { cons ->
                    binding.tvConsTitle.isVisible = true
                    binding.tvConsList.isVisible = true
                    binding.tvConsList.text = cons
                }
            }else {
                binding.tvProsTitle.isVisible = false
                binding.tvProsList.isVisible = false
                binding.tvConsTitle.isVisible = false
                binding.tvConsList.isVisible = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun buildProsString(data: CarEntity): SpannableString? {
        if(data.prosList.isNullOrEmpty()) return null
        return createSpannableStringList(data.prosList)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun buildConsString(data: CarEntity): SpannableString? {
        if(data.consList.isNullOrEmpty()) return null
        return createSpannableStringList(data.consList)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun createSpannableStringList(strList: List<String?>): SpannableString {
        var builder = SpannableStringBuilder()
        strList.forEachIndexed { index, str ->
            if(!str.isNullOrEmpty()){
                val string = SpannableString(str)
                string.setSpan(
                    binding.root.context?.getColor(R.color.orange)?.let { BulletSpan(40, it, 10) },
                    0,
                    string.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                builder.append(string)
                if(index < strList.lastIndex) builder.append("\n")
            }
        }
        return SpannableString(builder)
    }

}