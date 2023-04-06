package bell.test.guidomia_challenge.utils.helper

object FunctionHelper {

    fun Int.getPriceToDisplay(): String {
        return when {
            this >= 1000000 -> "${this.div(1000000)}M"
            this >= 1000 -> "${this.div(1000)}k"
            else -> this.toString()
        }
    }
}