package marcelo.esser.com.bustimek.vvm.lines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lines.*
import marcelo.esser.com.bustimek.R
import marcelo.esser.com.bustimek.adapter.SogalLinesAdapter
import marcelo.esser.com.bustimek.delegate.SogalLinesAdapterDelegate
import marcelo.esser.com.bustimek.model.sogal.LinesDTO

class LinesActivity : AppCompatActivity(), SogalLinesAdapterDelegate {

    private val viewModel: LinesActivityViewModel by lazy {
        LinesActivityViewModel()
    }
    private lateinit var adapter: SogalLinesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lines)
        viewModel.loadSogalLines(onSucces = {
            adapterConstruct(it)
        }, onError = {
            Toast.makeText(this@LinesActivity, "Ops", Toast.LENGTH_SHORT).show()
        })
    }

    private fun adapterConstruct(it: List<LinesDTO>) {
        adapter = SogalLinesAdapter(this@LinesActivity, it, this)
        lines_activity_rv_lines.adapter = adapter
    }
}
