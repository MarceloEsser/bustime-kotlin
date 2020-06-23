package esser.marcelo.busoclock.vvm.sogal.itineraries

import android.os.Bundle
import android.widget.Toast
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.ItinerariesAdapter
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.model.sogal.ItinerariesDTO
import esser.marcelo.busoclock.vvm.BaseActivity
import kotlinx.android.synthetic.main.activity_itineraries.*

class SogalItinerariesActivity : BaseActivity() {

    private val viewModel: SogalItinerariesActivityViewModel by lazy {
        SogalItinerariesActivityViewModel()
    }

    private lateinit var itinerariesAdapter: ItinerariesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itineraries)

        btnBack()
        loadItineraries()

        itineraries_activity_tv_line_name.text = LineDAO.lineName
    }

    private fun btnBack() {
        itineraries_activity_img_btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun loadItineraries() {
        showLoader()
        viewModel.loadItineraries(
            onSucces = {
                adapterConstruct(it)
            }, onError = {
                Toast.makeText(this@SogalItinerariesActivity, it, Toast.LENGTH_SHORT).show()
            })
    }

    private fun adapterConstruct(it: List<ItinerariesDTO>?) {
        hideLoader()
        itinerariesAdapter = ItinerariesAdapter(this@SogalItinerariesActivity, it)
        itineraries_activity_rv_itineraries.adapter = itinerariesAdapter
    }
}
