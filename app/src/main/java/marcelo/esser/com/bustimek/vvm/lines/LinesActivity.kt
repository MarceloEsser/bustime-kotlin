package marcelo.esser.com.bustimek.vvm.lines

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lines.*
import marcelo.esser.com.bustimek.R
import marcelo.esser.com.bustimek.adapter.SogalLinesAdapter
import marcelo.esser.com.bustimek.delegate.SogalLinesAdapterDelegate
import marcelo.esser.com.bustimek.helper.ProgressDialogHelper
import marcelo.esser.com.bustimek.model.sogal.LinesDTO
import marcelo.esser.com.bustimek.vvm.schedules.SchedulesActivity

class LinesActivity : AppCompatActivity(), SogalLinesAdapterDelegate {

    private val viewModel: LinesActivityViewModel by lazy {
        LinesActivityViewModel()
    }

    private val progressDialog: ProgressDialogHelper by lazy {
        ProgressDialogHelper(this@LinesActivity)
    }

    private lateinit var adapter: SogalLinesAdapter
    private var lineWay: String = "buscaHorarioLinhaCB"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lines)
        activity_lines_et_search.clearFocus()

        loadLines()

        bottomNavigationBarListener()
    }

    private fun loadLines() {
        progressDialog.showLoader()
        viewModel.loadSogalLines(onSucces = {
            adapterConstruct(it)
        }, onError = {
            progressDialog.hideLoader()
            Toast.makeText(this@LinesActivity, "Ops", Toast.LENGTH_SHORT).show()
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

    private fun adapterConstruct(it: List<LinesDTO>) {
        adapter = SogalLinesAdapter(this@LinesActivity, it, this)
        lines_activity_rv_lines.adapter = adapter
        progressDialog.hideLoader()
    }

    override fun onLineCLickListener(lineCode: String, lineName: String) {
        viewModel.saveData(lineCode, lineName, lineWay)
        startActivity(Intent(this@LinesActivity, SchedulesActivity::class.java))
    }
}
