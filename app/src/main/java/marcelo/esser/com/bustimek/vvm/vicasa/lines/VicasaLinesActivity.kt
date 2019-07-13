package marcelo.esser.com.bustimek.vvm.vicasa.lines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lines.*
import marcelo.esser.com.bustimek.R
import marcelo.esser.com.bustimek.adapter.VicasaLinesAdapter
import marcelo.esser.com.bustimek.helper.ProgressDialogHelper
import marcelo.esser.com.bustimek.interfaces.FilterDialogInteraction
import marcelo.esser.com.bustimek.interfaces.VicasaLinesAdapterDelegate
import marcelo.esser.com.bustimek.model.vicasa.VicasaFilterObject
import marcelo.esser.com.bustimek.vvm.vicasa.filterDialog.VicasaFilterDialog

class VicasaLinesActivity : AppCompatActivity(), FilterDialogInteraction, VicasaLinesAdapterDelegate {

    private val viewModel: VicasaLinesActivityViewModel by lazy {
        VicasaLinesActivityViewModel()
    }

    private val progressDialog: ProgressDialogHelper by lazy {
        ProgressDialogHelper(this@VicasaLinesActivity)
    }

    private lateinit var dialog: VicasaFilterDialog

    private lateinit var adapter: VicasaLinesAdapter
    private var lineWay: String = "buscaHorarioLinhaCB"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lines)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        dialogConstruction()
        bottomNavigationBarListener()
        filterDialogAction()
    }

    private fun dialogConstruction() {
        dialog = VicasaFilterDialog()
        dialog.interaction = this

        dialog.show(supportFragmentManager, "teste")
    }

    private fun filterDialogAction() {
        activity_lines_imgbtn_filter.setOnClickListener {
            dialog.show(supportFragmentManager, "teste")
        }
    }

    private fun bottomNavigationBarListener() {
        lines_bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_cb -> {
                    lineWay = "buscaHorarioLinhaCB"
                    true
                }
                R.id.action_bc -> {
                    lineWay = "buscaHorarioLinhaBC"
                    true
                }
                else -> false
            }
        }
    }

    private fun adapterConstruct(it: List<VicasaFilterObject>) {
        adapter = VicasaLinesAdapter(it, this@VicasaLinesActivity, this)
        lines_activity_rv_lines.adapter = adapter
        progressDialog.hideLoader()
    }

    override fun test(vicasaLineId: String) {
        Toast.makeText(this@VicasaLinesActivity, vicasaLineId, Toast.LENGTH_LONG).show()
    }

    override fun doFilter(countryOridin: String, countryDestination: String, serviceType: String) {
        progressDialog.showLoader()
        viewModel.loadVicasaLinesBy(
            lineOrigin = countryOridin,
            lineDestination = countryDestination,
            lineService = serviceType,
            onSuccess = {
                progressDialog.hideLoader()
                adapterConstruct(it)
            }, onError = {
                progressDialog.hideLoader()
                Toast.makeText(this@VicasaLinesActivity, "Ops", Toast.LENGTH_SHORT).show()
            })
    }
}
