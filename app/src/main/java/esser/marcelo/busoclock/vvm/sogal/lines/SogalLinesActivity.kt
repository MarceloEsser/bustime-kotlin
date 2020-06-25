package esser.marcelo.busoclock.vvm.sogal.lines

import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.*
import android.view.WindowManager
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.GenericLinesAdapter
import esser.marcelo.busoclock.extensions.hideKeyboard
import esser.marcelo.busoclock.helper.Constants.CB_WAY
import esser.marcelo.busoclock.interfaces.GenericLinesAdapterDelegate
import esser.marcelo.busoclock.model.BaseLine
import esser.marcelo.busoclock.model.sogal.LinesDTO
import esser.marcelo.busoclock.vvm.BaseActivity
import esser.marcelo.busoclock.vvm.lineDialog.LineMenuDialog
import kotlinx.android.synthetic.main.activity_lines.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SogalLinesActivity : BaseActivity(), GenericLinesAdapterDelegate {

    private val viewModel: SogalLinesActivityViewModel by lazy {
        SogalLinesActivityViewModel()
    }

    lateinit var lineMenuDialog: LineMenuDialog

    private lateinit var adapter: GenericLinesAdapter
    var lineWay: String = CB_WAY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lines)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        activity_lines_imgbtn_filter.visibility = GONE

        listeners()
    }

    override fun onStart() {
        super.onStart()
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
                adapterConstruct(viewModel.linesList.filter {
                    it.name.toLowerCase().contains(activity_lines_et_search.text.toString().toLowerCase())
                            || it.code.toLowerCase().contains(activity_lines_et_search.text.toString().toLowerCase())
                })
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

            animate.duration = 900
            animate.start()
        } else if (activity_lines_et_search.text.isNullOrEmpty() &&lav_cancel_search_action.progress == 0.5f) {
            val animate = ValueAnimator.ofFloat(0.5f, 0f)

            animate.addUpdateListener {
                lav_cancel_search_action.progress = it.animatedValue as Float
            }
            animate.duration = 900
            animate.start()
        }
    }

    private fun loadLines() {
        showLoader()
        GlobalScope.launch {
            delay(400L)
            viewModel.loadSogalLines(
                onSucces = {
                    adapterConstruct(it)
                }, onError = {
                    hideLoader()
                    errorConfig()
                })
        }
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

    private fun adapterConstruct(it: List<LinesDTO>) {
        adapter = GenericLinesAdapter(it, this@SogalLinesActivity, this)
        lines_activity_rv_lines.adapter = adapter
        successConfig()
    }

    override fun onItemClickLitener(line: BaseLine) {
        lineMenuDialog = LineMenuDialog(false, this)

        viewModel.saveData(line.code, line.name)
        lineMenuDialog.show(supportFragmentManager, "lineMenuDialog")
    }

}
