package esser.marcelo.busoclock.view.activity

import android.content.Intent
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.Observer
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.view.adapter.FavoriteLinesAdapter
import esser.marcelo.busoclock.repository.dao.LineDAO
import esser.marcelo.busoclock.interfaces.DeleteDelegate
import esser.marcelo.busoclock.interfaces.IFavoriteLineAdapterDelegate
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.view.dialog.DeleteDialog
import esser.marcelo.busoclock.viewModel.FavoriteLinesViewModel
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
            lines_activity_rv_lines.visibility = VISIBLE
            lines_activity_rv_lines.adapter = adapter
        }

        viewModel.favoriteLines.observe(this, linesListObserver)
    }

    override fun onInitValues() {
        activity_home_title.text = "Linhas salvas"
        lines_activity_img_btn_delete_all.visibility = VISIBLE

        lines_activity_img_btn_delete_all.setOnClickListener {
            deleteDialog = DeleteDialog(this)
            deleteDialog.show(supportFragmentManager, "teste")
        }

        lines_activity_img_btn_back.setOnClickListener {
            onBackPressed()
        }
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