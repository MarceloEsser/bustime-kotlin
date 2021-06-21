package esser.marcelo.busoclock.view.activity.favorite

import android.content.Intent
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.Observer
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.view.adapter.FavoriteLinesAdapter
import esser.marcelo.busoclock.repository.LineHolder
import esser.marcelo.busoclock.interfaces.DeleteDelegate
import esser.marcelo.busoclock.interfaces.IFavoriteLineAdapterDelegate
import esser.marcelo.busoclock.model.LineWay
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.view.activity.BaseActivity
import esser.marcelo.busoclock.view.dialog.DeleteDialog
import esser.marcelo.busoclock.viewModel.favorite.FavoriteLinesViewModel
import kotlinx.android.synthetic.main.activity_lines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class FavoriteLinesActivity : BaseActivity(R.layout.activity_lines), IFavoriteLineAdapterDelegate,
    DeleteDelegate {
    private val viewModel: FavoriteLinesViewModel by viewModel()
    private lateinit var adapter: FavoriteLinesAdapter

    lateinit var deleteDialog: DeleteDialog

    override fun observers() {
        linesObserver()
    }

    private fun linesObserver() {
        val linesListObserver = Observer<List<LineWithSchedules>> { lines ->
            adapter = FavoriteLinesAdapter(lines, this@FavoriteLinesActivity, this)
            if (lines != null && lines.isNotEmpty()) {
                lines_activity_tv_without_lines.visibility = GONE
                lines_activity_rv_lines.visibility = VISIBLE
                lines_activity_rv_lines.adapter = adapter
            } else {
                lines_activity_rv_lines.visibility = GONE
                lines_activity_tv_without_lines.visibility = VISIBLE
            }
        }

        viewModel.favoriteLines.observe(this, linesListObserver)
    }

    override fun onInitValues() {
        activity_home_title.text = "Linhas salvas"
        lines_activity_img_btn_delete_all.visibility = VISIBLE
        clSearch.visibility = GONE

        lines_activity_img_btn_delete_all.setOnClickListener {
            deleteDialog = DeleteDialog(this)
            deleteDialog.show(supportFragmentManager, "teste")
        }

        lines_activity_img_btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onLineClicked(line: FavoriteLine) {
        LineHolder.lineCode = line.code
        LineHolder.lineName = line.name
        LineHolder.lineId = line.id
        val newWay = LineWay(description = line.way, way = null)
        LineHolder.lineWay = newWay
        startActivity(
            Intent(
                this@FavoriteLinesActivity,
                FavoriteSchedulesAcitivty::class.java
            )
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        adapter.notifyDataSetChanged()
    }

    override fun doDelete() {
        viewModel.deleteAll()
        Toast.makeText(this, R.string.lines_deleted, Toast.LENGTH_SHORT).show()
        finish()
    }
}