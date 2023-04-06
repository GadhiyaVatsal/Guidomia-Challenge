package bell.test.guidomia_challenge.utils.helper

import android.content.Context
import android.widget.Toast
import bell.test.guidomia_challenge.R
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
}