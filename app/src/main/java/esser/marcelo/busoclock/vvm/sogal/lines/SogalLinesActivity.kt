package esser.marcelo.busoclock.vvm.sogal.lines

import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.*
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.GenericLinesAdapter
import esser.marcelo.busoclock.extensions.hideKeyboard
import esser.marcelo.busoclock.helper.Constants.CB_WAY
import esser.marcelo.busoclock.interfaces.GenericLinesAdapterDelegate
import esser.marcelo.busoclock.interfaces.LineMenuDelegate
import esser.marcelo.busoclock.model.BaseLine
import esser.marcelo.busoclock.model.sogal.LinesDTO
import esser.marcelo.busoclock.vvm.BaseActivity
import esser.marcelo.busoclock.vvm.lineDialog.LineMenuDialog
import kotlinx.android.synthetic.main.activity_lines.*


class SogalLinesActivity : BaseActivity(), GenericLinesAdapterDelegate, LineMenuDelegate {

    private val viewModel: SogalLinesViewModel by viewModels()

    private lateinit var lineMenuDialog: LineMenuDialog

    private lateinit var adapter: GenericLinesAdapter

    var lineWay: String = CB_WAY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lines)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        viewModel.initializeDao(this)

        listeners()

        linesObserver()

        isFavoriteObserver()
    }

    private fun isFavoriteObserver() {
        val isFavoriteObserver = Observer<Boolean> {
            lineMenuDialog.isFavorite = it
            lineMenuDialog.validateImageButton()
        }

        viewModel.isFavorite.observe(this, isFavoriteObserver)
    }

    private fun linesObserver() {
        val linesObserver = Observer<List<LinesDTO>> { lines ->
            adapterConstruct(lines)
        }
        viewModel.lines.observe(this, linesObserver)
    }

    override fun onResume() {
        super.onResume()
        loadLines()
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

    private fun loadLines() {
        showLoader()
        viewModel.loadSogalLines(onError = {
            hideLoader()
            errorConfig()
        })
    }

    private fun errorConfig() {
        lines_activity_img_lottie_conection.resumeAnimation()

        lines_activity_img_lottie_conection.setOnClickListener {
            lines_activity_img_lottie_conection.pauseAnimation()
            loadLines()
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
            activityContext = this@SogalLinesActivity,
            isFavorite = viewModel.isFavorite.value ?: false,
            delegate = this,
            lineWays = viewModel.getWaysList()
        )

        lineMenuDialog.show(supportFragmentManager, "lineMenuDialog")
    }

    override fun goToSchedules() {

    }

    override fun findLine() {
        viewModel.findLine()
    }

    override fun saveLine() {

    }

    override fun removeLine() {

    }

    override fun goToItineraries() {

    }

}
