package esser.marcelo.busoclock.view.activity.favorite

import androidx.lifecycle.Observer
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.model.sogal.ItinerariesDTO
import esser.marcelo.busoclock.repository.LineHolder
import esser.marcelo.busoclock.view.activity.BaseActivity
import esser.marcelo.busoclock.view.adapter.ItinerariesAdapter
import esser.marcelo.busoclock.viewModel.favorite.FavoriteItinerariesViewModel
import kotlinx.android.synthetic.main.activity_itineraries.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteItinerariesActivity: BaseActivity(R.layout.activity_itineraries) {
    private val viewModel: FavoriteItinerariesViewModel by viewModel()
    private lateinit var itinerariesAdapter: ItinerariesAdapter

    override fun observers() {
        val itinerariesListObserver = Observer<List<ItinerariesDTO>> { itineraries ->
            hideLoader()
            adapterConstruct(itineraries)
        }

        viewModel.itineraries.observe(this, itinerariesListObserver)
    }

    override fun onInitValues() {
        btnBack()
        showLoader()

        itineraries_activity_tv_line_name.text = LineHolder.lineName
    }

    private fun btnBack() {
        itineraries_activity_img_btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun adapterConstruct(itineraries: List<ItinerariesDTO>) {
        itinerariesAdapter = ItinerariesAdapter(this, itineraries)
        itineraries_activity_rv_itineraries.adapter = itinerariesAdapter
    }
}