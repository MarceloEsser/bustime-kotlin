package esser.marcelo.busoclock.view.activity

import android.animation.ValueAnimator
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View.*
import android.widget.Toast
import androidx.lifecycle.Observer
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.extensions.hideKeyboard
import esser.marcelo.busoclock.model.Constants.CB_WAY
import esser.marcelo.busoclock.interfaces.GenericLinesAdapterDelegate
import esser.marcelo.busoclock.interfaces.LineMenuDelegate
import esser.marcelo.busoclock.model.BaseLine
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.sogal.LinesDTO
import esser.marcelo.busoclock.view.adapter.GenericLinesAdapter
import esser.marcelo.busoclock.view.dialog.LineMenuDialog
import esser.marcelo.busoclock.viewModel.SogalLinesViewModel
import kotlinx.android.synthetic.main.activity_lines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class SogalLinesActivity : BaseActivity(R.layout.activity_lines), GenericLinesAdapterDelegate,
    LineMenuDelegate {

    private val viewModel: SogalLinesViewModel by viewModel()

    lateinit var lineMenuDialog: LineMenuDialog

    private lateinit var adapter: GenericLinesAdapter
    private val mContext = this

    var lineWay: String = CB_WAY

    override fun onInitValues() {
        showLoader()
        listeners()

        linesObserver()

        isFavoriteObserver()

        val favoriteLineObserver = Observer<FavoriteLine> { line ->
            Toast.makeText(mContext, "${line.name} salva com sucesso", Toast.LENGTH_SHORT).show()
        }

        viewModel.mFavoriteLine.observe(mContext, favoriteLineObserver)
    }

    private fun isFavoriteObserver() {
        val isFavoriteObserver = Observer<Boolean> {
            lineMenuDialog.isFavorite = it
            lineMenuDialog.validateImageButton()
        }

        viewModel.isLineFavorite.observe(this, isFavoriteObserver)
    }

    private fun linesObserver() {
        val linesObserver = Observer<List<LinesDTO>> { lines ->
            hideLoader()
            adapterConstruct(lines)
        }
        viewModel.lines.observe(this, linesObserver)
    }

    private fun listeners() {
        lav_cancel_search_action.setOnClickListener {
            if (activity_lines_et_search.text.isNotEmpty()) {
                activity_lines_et_search.setText("")
                this.hideKeyboard()
            }
        }
        searchEvent()

        ibBacAction()
    }

    private fun ibBacAction() {
        lines_activity_img_btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun searchEvent() {
        activity_lines_et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.filterBy(
                    activity_lines_et_search.text.toString()
                )
                searchAnimationControll()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun searchAnimationControll() {
        if (activity_lines_et_search.text.isNotEmpty() && lav_cancel_search_action.progress == 0f) {
            val animate = ValueAnimator.ofFloat(0f, 0.5f)

            animate.addUpdateListener {
                lav_cancel_search_action.progress = it.animatedValue as Float
            }

            animate.duration = 700
            animate.start()
        } else if (activity_lines_et_search.text.isNullOrEmpty() && lav_cancel_search_action.progress == 0.5f) {
            val animate = ValueAnimator.ofFloat(0.5f, 0f)

            animate.addUpdateListener {
                lav_cancel_search_action.progress = it.animatedValue as Float
            }
            animate.duration = 500
            animate.start()
        }
    }

    private fun errorConfig() {
        lines_activity_img_lottie_conection.resumeAnimation()

        lines_activity_img_lottie_conection.setOnClickListener {
            lines_activity_img_lottie_conection.pauseAnimation()
            //TODO: Review this
            //loadLines()
        }

        lines_activity_img_lottie_conection.visibility = VISIBLE
        lines_activity_tv_connection_error.visibility = VISIBLE
        lines_activity_rv_lines.visibility = INVISIBLE
    }

    private fun successConfig() {

        lines_activity_rv_lines.setOnClickListener(null)
        lines_activity_rv_lines.visibility = VISIBLE
        lines_activity_img_lottie_conection.visibility = GONE
        lines_activity_tv_connection_error.visibility = GONE

        hideLoader()
    }

    private fun adapterConstruct(lines: List<LinesDTO>) {
        adapter = GenericLinesAdapter(lines, this@SogalLinesActivity, this)
        lines_activity_rv_lines.adapter = adapter
        successConfig()
    }

    override fun onItemClickLitener(line: BaseLine) {
        viewModel.saveData(line.code, line.name)
        showDialog()
    }

    private fun showDialog() {
        lineMenuDialog = LineMenuDialog(
            delegate = this,
            lineWays = viewModel.getWaysList()
        )

        lineMenuDialog.show(supportFragmentManager, "lineMenuDialog")
    }

    override fun findLine() {
        viewModel.validateLine()
    }

    override fun saveLine() {
        viewModel.saveLine()
    }

    override fun removeLine() {
        viewModel.deleteLine()
    }

    override fun goToSchedules() {
        startActivity(Intent(this, SogalSchedulesActivity::class.java))
    }

    override fun goToItineraries() {
        startActivity(Intent(this, SogalItinerariesActivity::class.java))
    }

}
