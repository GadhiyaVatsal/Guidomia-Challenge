package bell.test.guidomia_challenge.utils.helper

import android.content.Context
import android.widget.Toast
import bell.test.guidomia_challenge.R
import bell.test.guidomia_challenge.ui.cars.fragment.entity.CarEntity
import bell.test.guidomia_challenge.utils.Constants

object FunctionHelper {
    fun Int.getPriceToDisplay(): String {
        return when {
            this >= 1000000 -> "${this.div(1000000)}M"
            this >= 1000 -> "${this.div(1000)}k"
            else -> this.toString()
        }
    }

    fun showShortToast(context: Context, msg: String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun expandContainerHelper(position: Int, isFirst: Boolean = false, currentList: ArrayList<CarEntity>): ArrayList<CarEntity> {
        if(isFirst) currentList[position].expanded = true
        else currentList[position].expanded =
            !currentList[position].expanded

        currentList.map {
            if (currentList.indexOf(it) != position) {
                it.expanded = false
            }
        }

        return currentList
    }
}