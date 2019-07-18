package esser.marcelo.busoclock.vvm.vicasa.lines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lines.*
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.GenericLinesAdapter
import esser.marcelo.busoclock.helper.Constants.BC_WAY
import esser.marcelo.busoclock.helper.Constants.CB_WAY
import esser.marcelo.busoclock.helper.ProgressDialogHelper
import esser.marcelo.busoclock.interfaces.FilterDialogInteraction
import esser.marcelo.busoclock.interfaces.GenericLinesAdapterDelegate
import esser.marcelo.busoclock.model.BaseLine
import esser.marcelo.busoclock.model.vicasa.Vicasa
import esser.marcelo.busoclock.vvm.lineDialog.LineMenuDialog
import esser.marcelo.busoclock.vvm.vicasa.filterDialog.VicasaFilterDialog

class VicasaLinesActivity : AppCompatActivity(), FilterDialogInteraction, GenericLinesAdapterDelegate {

    private val viewModel: VicasaLinesActivityViewModel by lazy {
        VicasaLinesActivityViewModel()
    }

    private val progressDialog: ProgressDialogHelper by lazy {
        ProgressDialogHelper(this@VicasaLinesActivity)
    }

    private val lineMenuDialog: LineMenuDialog by lazy {
        LineMenuDialog(true)
    }

    private lateinit var dialog: VicasaFilterDialog
    private lateinit var adapter: GenericLinesAdapter

    private var lineWay: String = CB_WAY
    private var countryOrigin: String = ""
    private var countryDestination: String = ""
    private var serviceType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lines)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        buildDialog()
        bottomNavigationBarListener()
        dialogDoFilter()
    }

    private fun buildDialog() {
        dialog = VicasaFilterDialog()
        dialog.interaction = this

        dialog.show(supportFragmentManager, "teste")
    }

    private fun dialogDoFilter() {
        activity_lines_imgbtn_filter.setOnClickListener {
            dialog.show(supportFragmentManager, "teste")
        }
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

    private fun adapterConstruct(it: List<Vicasa>) {
        successConfig()
        adapter = GenericLinesAdapter(it, this@VicasaLinesActivity, this)
        lines_activity_rv_lines.adapter = adapter
        lines_activity_rv_lines.visibility = VISIBLE
        progressDialog.hideLoader()
    }

    override fun onItemClickLitener(line: BaseLine) {
        viewModel.saveData(line.code, line.name, lineWay)
        lineMenuDialog.show(supportFragmentManager, "lineMenuDialog")
    }

    private fun successConfig() {
        lines_activity_rv_lines.setOnClickListener(null)
        lines_activity_rv_lines.visibility = VISIBLE
        lines_activity_img_lottie_conection.visibility = View.GONE
        lines_activity_tv_connection_error.visibility = View.GONE
    }

    private fun onError() {
        lines_activity_img_lottie_conection.resumeAnimation()

        lines_activity_img_lottie_conection.setOnClickListener {
            lines_activity_img_lottie_conection.pauseAnimation()
            doFilter(countryOrigin, countryDestination, serviceType)
        }

        lines_activity_img_lottie_conection.visibility = VISIBLE
        lines_activity_tv_connection_error.visibility = VISIBLE
        lines_activity_rv_lines.visibility = View.INVISIBLE
    }

    override fun doFilter(countryOrigin: String, countryDestination: String, serviceType: String) {
        progressDialog.showLoader()
        this.countryOrigin = countryOrigin
        this.countryDestination = countryDestination
        this.serviceType = serviceType

        viewModel.loadVicasaLinesBy(
            lineOrigin = countryOrigin,
            lineDestination = countryDestination,
            lineService = serviceType,
            onSuccess = {
                progressDialog.hideLoader()
                adapterConstruct(it)
            }, onError = {
                progressDialog.hideLoader()
                onError()
            })
    }
}
