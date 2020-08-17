package esser.marcelo.busoclock.vvm.vicasa.lines

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.GenericLinesAdapter
import esser.marcelo.busoclock.extensions.hideKeyboard
import esser.marcelo.busoclock.interfaces.FilterDialogInteraction
import esser.marcelo.busoclock.interfaces.GenericLinesAdapterDelegate
import esser.marcelo.busoclock.interfaces.LineMenuDelegate
import esser.marcelo.busoclock.model.BaseLine
import esser.marcelo.busoclock.model.vicasa.Vicasa
import esser.marcelo.busoclock.vvm.BaseActivity
import esser.marcelo.busoclock.vvm.lineDialog.LineMenuDialog
import esser.marcelo.busoclock.vvm.vicasa.filterDialog.VicasaFilterDialog
import kotlinx.android.synthetic.main.activity_lines.*

class VicasaLinesActivity : BaseActivity(), FilterDialogInteraction,
    GenericLinesAdapterDelegate, LineMenuDelegate {

    private val viewModel: VicasaLinesActivityViewModel by lazy {
        VicasaLinesActivityViewModel()
    }

    lateinit var menuDialog: LineMenuDialog

    private lateinit var filterDialog: VicasaFilterDialog
    private lateinit var adapter: GenericLinesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lines)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        activity_lines_imgbtn_filter.visibility = VISIBLE

        buildDialog()

        dialogDoFilter()

        listeners()

    }

    private fun listeners() {
        searchEvent()
        ibBackAction()
    }

    private fun ibBackAction() {
        lines_activity_img_btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun searchEvent() {
        activity_lines_et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                configureList(viewModel.resultsList.filter {
                    it.name.toLowerCase()
                        .contains(activity_lines_et_search.text.toString().toLowerCase())
                            || it.code.toLowerCase()
                        .contains(activity_lines_et_search.text.toString().toLowerCase())
                })
            }
        })
    }

    private fun buildDialog() {
        filterDialog = VicasaFilterDialog()
        filterDialog.interaction = this

        filterDialog.show(supportFragmentManager, "teste")
    }

    private fun dialogDoFilter() {
        activity_lines_imgbtn_filter.setOnClickListener {
            filterDialog.show(supportFragmentManager, "teste")
        }
    }

    private fun configureList(it: List<Vicasa>) {
        adapter = GenericLinesAdapter(it, this@VicasaLinesActivity, this)
        lines_activity_rv_lines.adapter = adapter
        lines_activity_rv_lines.visibility = VISIBLE
    }

    override fun onItemClickLitener(line: BaseLine) {
        viewModel.saveLineData(line.code, line.name)
        menuDialog = LineMenuDialog(
            isFavorite = false,
            delegate = this,
            lineWays = viewModel.getWaysList()
        )
        menuDialog.show(supportFragmentManager, "menuDialog")
    }

    private fun successConfig() {
        lines_activity_rv_lines.setOnClickListener(null)
        lines_activity_rv_lines.visibility = VISIBLE
        lines_activity_img_lottie_conection.visibility = GONE
        lines_activity_tv_connection_error.visibility = GONE

        hideLoader()
    }

    private fun errorConfig() {
        lines_activity_img_lottie_conection.resumeAnimation()

        lottieAnimationClick()

        lines_activity_img_lottie_conection.visibility = VISIBLE
        lines_activity_tv_connection_error.visibility = VISIBLE
        lines_activity_rv_lines.visibility = View.INVISIBLE

        hideLoader()
    }

    private fun lottieAnimationClick() {
        lines_activity_img_lottie_conection.setOnClickListener {
            lines_activity_img_lottie_conection.pauseAnimation()
            this.hideKeyboard()
            doFilter(viewModel.countryOrigin, viewModel.countryDestination, viewModel.serviceType)
        }
    }

    override fun doFilter(countryOrigin: String, countryDestination: String, serviceType: String) {
        showLoader()

        viewModel.saveFilterData(countryOrigin, countryDestination, serviceType)

        viewModel.loadVicasaLinesBy(
            onSuccess = {
                successConfig()
                if (it.isNotEmpty()) {
                    lines_activity_tv_without_lines.visibility = GONE
                    configureList(it)
                } else {
                    lines_activity_tv_without_lines.visibility = VISIBLE
                }
            }, onError = {
                errorConfig()
            })
    }

    override fun saveLine() {

    }

    override fun removeLine() {

    }

    override fun goToItineraries() {

    }

    override fun findLine() {

    }

}
