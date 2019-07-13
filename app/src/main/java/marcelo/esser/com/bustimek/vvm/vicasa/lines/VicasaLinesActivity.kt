package marcelo.esser.com.bustimek.vvm.vicasa.lines

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lines.*
import marcelo.esser.com.bustimek.R
import marcelo.esser.com.bustimek.adapter.VicasaLinesAdapter
import marcelo.esser.com.bustimek.helper.ProgressDialogHelper
import marcelo.esser.com.bustimek.interfaces.FilterDialogInteraction
import marcelo.esser.com.bustimek.interfaces.SogalLinesAdapterDelegate
import marcelo.esser.com.bustimek.vvm.sogal.schedules.SogalSchedulesActivity
import marcelo.esser.com.bustimek.vvm.vicasa.filterDialog.VicasaFilterDialog

class VicasaLinesActivity : AppCompatActivity(), FilterDialogInteraction {

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
        dialog = VicasaFilterDialog()
        dialog.interaction = this

        loadLines()

        bottomNavigationBarListener()
        activity_lines_imgbtn_filter.setOnClickListener {
            dialog.show(supportFragmentManager, "teste")
        }
    }

    private fun loadLines() {
        progressDialog.showLoader()
        viewModel.loadVicasaLines(
            onSucces = {
                progressDialog.hideLoader()
                adapterConstruct(it)
            }, onError = {
                progressDialog.hideLoader()
                Toast.makeText(this@VicasaLinesActivity, "Ops", Toast.LENGTH_SHORT).show()
            })
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

    private fun adapterConstruct(it: List<String>) {
        adapter = VicasaLinesAdapter(it, this@VicasaLinesActivity)
        lines_activity_rv_lines.adapter = adapter
        progressDialog.hideLoader()
    }

    override fun filter(countryOridin: String, countryDestination: String, serviceType: String) {
        progressDialog.showLoader()
        viewModel.loadVicasaLines(
            lineOrigin = countryOridin,
            lineDestination = countryDestination,
            linService = serviceType,
            onSucces = {
                progressDialog.hideLoader()
                adapterConstruct(it)
            }, onError = {
                progressDialog.hideLoader()
                Toast.makeText(this@VicasaLinesActivity, "Ops", Toast.LENGTH_SHORT).show()
            })
    }
}
