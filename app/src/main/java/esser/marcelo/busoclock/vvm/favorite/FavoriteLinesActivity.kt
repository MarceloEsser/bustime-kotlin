package esser.marcelo.busoclock.vvm.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.room.Delete
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.FavoriteLinesAdapter
import esser.marcelo.busoclock.adapter.GenericLinesAdapter
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.interfaces.DeleteDelegate
import esser.marcelo.busoclock.interfaces.IFavoriteLineAdapterDelegate
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.vvm.deleteDialog.DeleteDialog
import esser.marcelo.busoclock.vvm.favorite.schedules.FavoriteSchedulesAcitivty
import esser.marcelo.busoclock.vvm.lineDialog.LineMenuDialog
import kotlinx.android.synthetic.main.activity_lines.*
import kotlinx.android.synthetic.main.activity_schedules.*
import kotlinx.android.synthetic.main.dialog_line_menu.*

class FavoriteLinesActivity : AppCompatActivity(), IFavoriteLineAdapterDelegate, DeleteDelegate {

    private val viewModel: FavoriteLinesViewModel by viewModels()
    private lateinit var adapter: FavoriteLinesAdapter

    lateinit var deleteDialog: DeleteDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lines)

        viewModel.initializeDao(this@FavoriteLinesActivity)

        viewModel.fillFavoriteLinesList()

        lines_activity_img_btn_delete_all.visibility = VISIBLE

        lines_activity_img_btn_delete_all.setOnClickListener {
            deleteDialog = DeleteDialog(this)
            deleteDialog.show(supportFragmentManager, "teste")
        }

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

    override fun doDelete() {
        viewModel.deleteAll()
        Toast.makeText(this, R.string.lines_deleted, Toast.LENGTH_SHORT).show()
        finish()
    }
}