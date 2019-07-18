package esser.marcelo.busoclock.vvm.sogal.lines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.*
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_lines.*
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.GenericLinesAdapter
import esser.marcelo.busoclock.helper.Constants.BC_WAY
import esser.marcelo.busoclock.helper.Constants.CB_WAY
import esser.marcelo.busoclock.helper.ProgressDialogHelper
import esser.marcelo.busoclock.interfaces.GenericLinesAdapterDelegate
import esser.marcelo.busoclock.model.BaseLine
import esser.marcelo.busoclock.model.sogal.LinesDTO
import esser.marcelo.busoclock.vvm.lineDialog.LineMenuDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SogalLinesActivity : AppCompatActivity(), GenericLinesAdapterDelegate {
    private val viewModelSogal: SogalLinesActivityViewModel by lazy {
        SogalLinesActivityViewModel()
    }

    private val progressDialog: ProgressDialogHelper by lazy {
        ProgressDialogHelper(this@SogalLinesActivity)
    }

    private val lineMenuDialog: LineMenuDialog by lazy {
        LineMenuDialog(false)
    }

    private lateinit var adapter: GenericLinesAdapter
    var lineWay: String = CB_WAY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lines)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        activity_lines_imgbtn_filter.visibility = GONE

        bottomNavigationBarListener()
    }

    private fun loadLines() {
        progressDialog.showLoader()
        GlobalScope.launch {
            delay(400L)
            viewModelSogal.loadSogalLines(
                onSucces = {
                    adapterConstruct(it)
                }, onError = {
                    progressDialog.hideLoader()
                    onError()
                })
        }
    }

    private fun onError() {
        lines_activity_img_lottie_conection.resumeAnimation()

        lines_activity_img_lottie_conection.setOnClickListener {
            lines_activity_img_lottie_conection.pauseAnimation()
            loadLines()
        }

        lines_activity_img_lottie_conection.visibility = VISIBLE
        lines_activity_tv_connection_error.visibility = VISIBLE
        lines_activity_rv_lines.visibility = INVISIBLE
    }

    private fun bottomNavigationBarListener() {
        lines_bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_cb -> {
                    lineWay = CB_WAY
                    true
                }
                R.id.action_bc -> {
                    lineWay = BC_WAY
                    true
                }
                else -> false
            }
        }
    }


    override fun onStart() {
        super.onStart()
        loadLines()
    }


    private fun adapterConstruct(it: List<LinesDTO>) {
        adapter = GenericLinesAdapter(it, this@SogalLinesActivity, this)
        lines_activity_rv_lines.adapter = adapter
        progressDialog.hideLoader()
        successConfig()
    }

    private fun successConfig() {
        lines_activity_rv_lines.setOnClickListener(null)
        lines_activity_rv_lines.visibility = VISIBLE
        lines_activity_img_lottie_conection.visibility = GONE
        lines_activity_tv_connection_error.visibility = GONE
    }

    override fun onItemClickLitener(line: BaseLine) {
        viewModelSogal.saveData(line.code, line.name, lineWay)
        lineMenuDialog.show(supportFragmentManager, "lineMenuDialog")
    }
}
