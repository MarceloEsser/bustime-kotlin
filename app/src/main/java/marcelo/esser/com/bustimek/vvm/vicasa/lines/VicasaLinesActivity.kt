package marcelo.esser.com.bustimek.vvm.vicasa.lines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lines.*
import marcelo.esser.com.bustimek.R
import marcelo.esser.com.bustimek.adapter.GenericLinesAdapter
import marcelo.esser.com.bustimek.helper.Constants.BC_WAY
import marcelo.esser.com.bustimek.helper.Constants.CB_WAY
import marcelo.esser.com.bustimek.helper.ProgressDialogHelper
import marcelo.esser.com.bustimek.interfaces.FilterDialogInteraction
import marcelo.esser.com.bustimek.interfaces.GenericLinesAdapterDelegate
import marcelo.esser.com.bustimek.model.vicasa.Vicasa
import marcelo.esser.com.bustimek.vvm.vicasa.filterDialog.VicasaFilterDialog

class VicasaLinesActivity : AppCompatActivity(), FilterDialogInteraction, GenericLinesAdapterDelegate {

    private val viewModel: VicasaLinesActivityViewModel by lazy {
        VicasaLinesActivityViewModel()
    }

    private val progressDialog: ProgressDialogHelper by lazy {
        ProgressDialogHelper(this@VicasaLinesActivity)
    }

    private lateinit var dialog: VicasaFilterDialog
    private lateinit var adapter: GenericLinesAdapter
    private var lineWay: String = CB_WAY

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
        adapter = GenericLinesAdapter(it, this@VicasaLinesActivity, this)
        lines_activity_rv_lines.adapter = adapter
        progressDialog.hideLoader()
    }

    override fun onItemClickLitener(lineCode: String, lineName: String) {
        Toast.makeText(this@VicasaLinesActivity, lineCode, Toast.LENGTH_LONG).show()
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
