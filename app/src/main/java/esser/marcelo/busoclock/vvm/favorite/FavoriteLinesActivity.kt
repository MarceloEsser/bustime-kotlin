package esser.marcelo.busoclock.vvm.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.FavoriteLinesAdapter
import esser.marcelo.busoclock.adapter.GenericLinesAdapter
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.interfaces.IFavoriteLineAdapterDelegate
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.vvm.favorite.schedules.FavoriteSchedulesAcitivty
import kotlinx.android.synthetic.main.activity_lines.*
import kotlinx.android.synthetic.main.activity_schedules.*
import kotlinx.android.synthetic.main.dialog_line_menu.*

class FavoriteLinesActivity : AppCompatActivity(), IFavoriteLineAdapterDelegate {

    private val viewModel: FavoriteLinesViewModel by viewModels()
    private lateinit var adapter: FavoriteLinesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lines)

        viewModel.initializeDao(this@FavoriteLinesActivity)

        viewModel.fillFavoriteLinesList()

        activity_lines_imgbtn_filter.visibility = View.GONE

        lines_activity_img_btn_back.setOnClickListener {
            onBackPressed()
        }

        val linesListObserver = Observer<List<FavoriteLine>> { lines ->
            adapter = FavoriteLinesAdapter(lines, this@FavoriteLinesActivity, this)
            lines_activity_rv_lines.visibility = VISIBLE
            lines_activity_rv_lines.adapter = adapter
        }

        viewModel.favoriteLines.observe(this, linesListObserver)
    }

    override fun onLineClicked(line: FavoriteLine) {
        LineDAO.lineCode = line.code
        LineDAO.lineName = line.name
        LineDAO.lineId = line.id
        startActivity(Intent(this@FavoriteLinesActivity, FavoriteSchedulesAcitivty::class.java))
    }
}