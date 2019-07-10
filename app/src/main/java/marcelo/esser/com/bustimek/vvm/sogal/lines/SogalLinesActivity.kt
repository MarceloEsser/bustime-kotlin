package marcelo.esser.com.bustimek.vvm.sogal.lines

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lines.*
import marcelo.esser.com.bustimek.R
import marcelo.esser.com.bustimek.adapter.SogalLinesAdapter
import marcelo.esser.com.bustimek.interfaces.SogalLinesAdapterDelegate
import marcelo.esser.com.bustimek.helper.ProgressDialogHelper
import marcelo.esser.com.bustimek.model.sogal.LinesDTO
import marcelo.esser.com.bustimek.vvm.sogal.schedules.SogalSchedulesActivity

class SogalLinesActivity : AppCompatActivity(), SogalLinesAdapterDelegate {

    private val viewModelSogal: SogalLinesActivityViewModel by lazy {
        SogalLinesActivityViewModel()
    }

    private val progressDialog: ProgressDialogHelper by lazy {
        ProgressDialogHelper(this@SogalLinesActivity)
    }

    private lateinit var adapter: SogalLinesAdapter
    private var lineWay: String = "buscaHorarioLinhaCB"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lines)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        activity_lines_imgbtn_filter.visibility = View.GONE


        loadLines()

        bottomNavigationBarListener()
    }

    private fun loadLines() {
        progressDialog.showLoader()
        viewModelSogal.loadSogalLines(
            onSucces = {
                adapterConstruct(it)
            }, onError = {
                progressDialog.hideLoader()
                Toast.makeText(this@SogalLinesActivity, "Ops", Toast.LENGTH_SHORT).show()
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
        adapter = SogalLinesAdapter(this@SogalLinesActivity, it, this)
        lines_activity_rv_lines.adapter = adapter
        progressDialog.hideLoader()
    }

    override fun onLineCLickListener(lineCode: String, lineName: String) {
        viewModelSogal.saveData(lineCode, lineName, lineWay)
        startActivity(Intent(this@SogalLinesActivity, SogalSchedulesActivity::class.java))
    }
}