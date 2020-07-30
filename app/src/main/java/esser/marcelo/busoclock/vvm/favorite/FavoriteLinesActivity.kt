package esser.marcelo.busoclock.vvm.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.FavoriteLinesAdapter
import esser.marcelo.busoclock.adapter.GenericLinesAdapter
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import kotlinx.android.synthetic.main.activity_lines.*
import kotlinx.android.synthetic.main.dialog_line_menu.*

class FavoriteLinesActivity : AppCompatActivity() {

    private val viewModel: FavoriteLinesViewModel by viewModels()
    private lateinit var adapter: FavoriteLinesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lines)

        val linesListObserver = Observer<List<FavoriteLine>> { lines ->
            adapter = FavoriteLinesAdapter(lines, this@FavoriteLinesActivity)
            lines_activity_rv_lines.adapter = adapter
        }

        viewModel.favoriteLines.observe(this, linesListObserver)

        viewModel.fillFavoriteLinesList()
    }
}