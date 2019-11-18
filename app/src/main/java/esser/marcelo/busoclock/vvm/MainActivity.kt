package esser.marcelo.busoclock.vvm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import esser.marcelo.busoclock.R
import kotlinx.android.synthetic.main.lines_bottomsheet_layout.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bn_line_way_selector.isSelected = false
    }
}
