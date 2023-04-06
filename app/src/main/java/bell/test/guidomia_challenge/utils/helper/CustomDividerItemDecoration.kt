package bell.test.guidomia_challenge.utils.helper

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomDividerItemDecoration(
    private val divider: Drawable,
    private val padding: Int,
) : RecyclerView.ItemDecoration() {

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft + padding
        val right = parent.width - parent.paddingRight - padding

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin + padding
            val bottom = top + divider.intrinsicHeight + 2

            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
            outRect.setEmpty()
        } else {
            outRect.set(0, 0, 0, divider.intrinsicHeight + (2*padding))
        }
    }
}