package esser.marcelo.busoclock.view.activity.sogal

import androidx.lifecycle.Observer
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.repository.LineHolder
import esser.marcelo.busoclock.model.sogal.ItinerariesDTO
import esser.marcelo.busoclock.view.activity.BaseActivity
import esser.marcelo.busoclock.view.adapter.ItinerariesAdapter
import esser.marcelo.busoclock.viewModel.SogalItinerariesViewModel
import kotlinx.android.synthetic.main.activity_itineraries.*
import kotlinx.android.synthetic.main.activity_lines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class SogalItinerariesActivity : BaseActivity(R.layout.activity_itineraries) {

    private val viewModel: SogalItinerariesViewModel by viewModel()

    private lateinit var itinerariesAdapter: ItinerariesAdapter

    override fun observers() {
        itinerariesObserver()
    }

    override fun onInitValues() {
        btnBack()
        showLoader()

        itineraries_activity_tv_line_name.text = LineHolder.lineName

    }

    private fun itinerariesObserver() {
        val itinerariesListObserver = Observer<List<ItinerariesDTO>> { itineraries ->
            hideLoader()
            adapterConstruct(itineraries)
        }

        viewModel.itineraries.observe(this, itinerariesListObserver)
    }

    private fun btnBack() {
        itineraries_activity_img_btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun adapterConstruct(itineraries: List<ItinerariesDTO>) {
        itinerariesAdapter = ItinerariesAdapter(this@SogalItinerariesActivity, itineraries)
        itineraries_activity_rv_itineraries.adapter = itinerariesAdapter
    }
}
