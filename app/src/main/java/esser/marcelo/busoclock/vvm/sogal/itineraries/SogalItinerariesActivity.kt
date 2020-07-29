package esser.marcelo.busoclock.vvm.sogal.itineraries

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.ItinerariesAdapter
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.model.sogal.ItinerariesDTO
import esser.marcelo.busoclock.vvm.BaseActivity
import kotlinx.android.synthetic.main.activity_itineraries.*

class SogalItinerariesActivity : BaseActivity() {

    private val viewModel: SogalItinerariesViewModel by viewModels()

    private lateinit var itinerariesAdapter: ItinerariesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_itineraries)

        btnBack()
        loadItineraries()

        itineraries_activity_tv_line_name.text = LineDAO.lineName

        itinerariesList()
    }

    private fun itinerariesList() {
        val itinerariesListObserver = Observer<List<ItinerariesDTO>> { itineraries ->
            adapterConstruct(itineraries)
        }

        viewModel.itineraries.observe(this, itinerariesListObserver)
    }

    private fun btnBack() {
        itineraries_activity_img_btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun loadItineraries() {
        showLoader()
        viewModel.loadItineraries()
    }

    private fun adapterConstruct(it: List<ItinerariesDTO>) {
        hideLoader()
        itinerariesAdapter = ItinerariesAdapter(this@SogalItinerariesActivity, it)
        itineraries_activity_rv_itineraries.adapter = itinerariesAdapter
    }
}
