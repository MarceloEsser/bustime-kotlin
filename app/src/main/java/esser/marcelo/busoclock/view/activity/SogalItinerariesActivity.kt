package esser.marcelo.busoclock.view.activity

import androidx.lifecycle.Observer
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.repository.dao.LineDAO
import esser.marcelo.busoclock.model.sogal.ItinerariesDTO
import esser.marcelo.busoclock.view.adapter.ItinerariesAdapter
import esser.marcelo.busoclock.viewModel.SogalItinerariesViewModel
import kotlinx.android.synthetic.main.activity_itineraries.*
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

        itineraries_activity_tv_line_name.text = LineDAO.lineName

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
