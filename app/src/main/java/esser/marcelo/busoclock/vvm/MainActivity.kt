package esser.marcelo.busoclock.vvm

import android.os.Bundle
import com.google.android.material.card.MaterialCardView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.appcompat.app.AppCompatActivity
import esser.marcelo.busoclock.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.lines_bottomsheet_layout.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bn_line_way_selector.isSelected = false

        cl_header.measure(0, 0)
        val headerHeight = cl_header.measuredHeight

        val from: BottomSheetBehavior<MaterialCardView> = BottomSheetBehavior.from(mcvBottomSheet)
        from.peekHeight = headerHeight

        bn_line_way_selector.inflateMenu(R.menu.vicasa_lines_bottom_navigation_menu)
    }
}
