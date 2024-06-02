package nextstep.omok

import android.os.Bundle
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    lateinit var putStoneLayout: GridLayout
    private val numRows = 15
    private val numCols = 15
    private var blackTurn = true
    val board = Array(numRows) { arrayOfNulls<String>(numCols) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun setImgviewListener(gridLayout: GridLayout) {
        for (i in 0 until gridLayout.childCount) {
            val imgView = gridLayout.getChildAt(i) as ImageView
            imgView.setOnClickListener {
                handleStonePlacement(imgView, i)
            }
        }
    }
}
