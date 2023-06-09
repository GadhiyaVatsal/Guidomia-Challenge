package bell.test.guidomia_challenge.ui.cars.fragment.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bell.test.guidomia_challenge.databinding.CarItemLayoutBinding
import bell.test.guidomia_challenge.ui.cars.fragment.CarsHomeViewModel
import bell.test.guidomia_challenge.ui.cars.fragment.entity.CarEntity
import bell.test.guidomia_challenge.ui.cars.fragment.viewholder.CarListViewHolder
import javax.inject.Inject

class CarListAdapter @Inject constructor(
    private val viewModel: CarsHomeViewModel
)  : RecyclerView.Adapter<CarListViewHolder>() {
    lateinit var binding: CarItemLayoutBinding

    private var carData = ArrayList<CarEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newData: List<CarEntity>) {
        val oldItems = ArrayList(this.carData)
        this.carData.clear()
        this.carData.addAll(newData)
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldItems.size
            }

            override fun getNewListSize(): Int {
                return carData.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newData[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newData[newItemPosition]
            }
        }).dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CarListViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(viewGroup.context)

        binding = CarItemLayoutBinding.inflate(
            inflater,
            viewGroup,
            false
        )
        viewHolder = CarListViewHolder(binding)
        return viewHolder
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: CarListViewHolder, position: Int) {
        carData[position].let { holder.setData(it, position, viewModel) }
    }

    override fun getItemCount(): Int {
        return carData.size
    }
}