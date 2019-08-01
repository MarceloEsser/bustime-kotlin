package esser.marcelo.busoclock.vvm.favorite

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.GenericLinesAdapter2
import esser.marcelo.busoclock.vvm.lineDialog.LineMenuDialog
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    private val lineMenuDialog: LineMenuDialog by lazy {
        LineMenuDialog(false)
    }

    private val viewModel: FavoriteActivityViewModel by lazy {
        FavoriteActivityViewModel()
    }

    private val adapter: GenericLinesAdapter2 by lazy {
        val lazyAdapter = GenericLinesAdapter2(viewModel.buildList(), this)
        lazyAdapter.onItemClickListener = this::onItemClickListener
        lazyAdapter.onFavoriteClickListener = this::onFavoriteClickListener
        lazyAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        rvFavorites.adapter = adapter

    }

    private fun onItemClickListener(line: GenericLinesAdapter2.Line) {
        viewModel.saveData(line.code, line.name, viewModel.lineWay)
        lineMenuDialog.show(supportFragmentManager, "lineMenuDialog")
    }

    private fun onFavoriteClickListener (line: GenericLinesAdapter2.Line, delete: Boolean) {
        if (delete) {
            viewModel.deleteFavorite(line) {}
        } else {
            viewModel.saveFavorite(viewModel.lineWay, line) {}
        }
    }
}
