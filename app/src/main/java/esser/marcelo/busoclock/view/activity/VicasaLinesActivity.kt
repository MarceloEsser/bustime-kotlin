package esser.marcelo.busoclock.view.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.view.adapter.GenericLinesAdapter
import esser.marcelo.busoclock.extensions.hideKeyboard
import esser.marcelo.busoclock.interfaces.FilterDialogInteraction
import esser.marcelo.busoclock.interfaces.GenericLinesAdapterDelegate
import esser.marcelo.busoclock.interfaces.LineMenuDelegate
import esser.marcelo.busoclock.model.BaseLine
import esser.marcelo.busoclock.model.vicasa.Vicasa
import esser.marcelo.busoclock.view.dialog.LineMenuDialog
import esser.marcelo.busoclock.view.dialog.VicasaFilterDialog
import esser.marcelo.busoclock.viewModel.VicasaLinesViewModel
import kotlinx.android.synthetic.main.activity_lines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class VicasaLinesActivity : BaseActivity(R.layout.activity_lines), FilterDialogInteraction,
    GenericLinesAdapterDelegate, LineMenuDelegate {

    private val viewModel: VicasaLinesViewModel by viewModel()

    lateinit var menuDialog: LineMenuDialog

    private lateinit var filterDialog: VicasaFilterDialog
    private lateinit var adapter: GenericLinesAdapter

    override fun onInitValues() {
        activity_lines_imgbtn_filter.visibility = VISIBLE

        buildDialog()

        dialogDoFilter()

        listeners()

        linesObserver()
    }

    private fun linesObserver() {
        val linesObserver = Observer<List<Vicasa>> { lines ->
            lines_activity_tv_without_lines.visibility = GONE
            hideLoader()
            configureList(lines)
            successConfig()
        }

        viewModel.lines.observe(this, linesObserver)
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
                configureList(
                    viewModel.lines.value?.filter { line ->
                        containsLineName(line) || containsLineCode(line)
                    } ?: listOf())
            }
        })
    }

    private fun containsLineName(line: Vicasa): Boolean {
        return line.name.toLowerCase(Locale.getDefault()).contains(
            activity_lines_et_search.text.toString()
                .toLowerCase(Locale.getDefault())
        )
    }

    private fun containsLineCode(line: Vicasa): Boolean {
        return line.code.toLowerCase(Locale.getDefault()).contains(
            activity_lines_et_search.text.toString()
                .toLowerCase(Locale.getDefault())
        )
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
        viewModel.loadLines()
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
