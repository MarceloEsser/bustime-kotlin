package esser.marcelo.busoclock.view.activity.sogal

import android.view.View
import androidx.lifecycle.Observer
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.repository.LineHolder
import esser.marcelo.busoclock.model.sogal.ItinerariesDTO
import esser.marcelo.busoclock.view.activity.BaseActivity
import esser.marcelo.busoclock.view.adapter.ItinerariesAdapter
import esser.marcelo.busoclock.viewModel.sogal.SogalItinerariesViewModel
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
        errorObserver()
    }

    override fun onInitValues() {
        btnBack()
        showLoader()

        itineraries_activity_tv_line_name.text = LineHolder.lineName

    }

    private fun errorObserver() {
        val errorOberver = Observer<String> { message ->
            hideLoader()
            var mMessage = message
            if(isNetworkAvailable(this)){
                mMessage = "Verifique sua conexão com a internet e tente novamente mais tarde"
            }
            showSnackBar(mMessage)
            errorConfig()
        }
        viewModel.error.observe(this, errorOberver)
    }


    private fun itinerariesObserver() {
        val itinerariesListObserver = Observer<List<ItinerariesDTO>> { itineraries ->
            successConfig()
            adapterConstruct(itineraries)
        }

        viewModel.itineraries.observe(this, itinerariesListObserver)
    }

    private fun btnBack() {
        itineraries_activity_img_btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun errorConfig() {
        itineraries_activity_img_lottie_conection.resumeAnimation()

        itineraries_activity_img_lottie_conection.setOnClickListener {
            itineraries_activity_img_lottie_conection.pauseAnimation()
            showLoader()
            viewModel.loadItineraries(null)
        }

        itineraries_activity_img_lottie_conection.visibility = View.VISIBLE
        itineraries_activity_tv_connection_error.visibility = View.VISIBLE
        itineraries_activity_rv_itineraries.visibility = View.INVISIBLE
    }

    private fun successConfig() {
        itineraries_activity_rv_itineraries.setOnClickListener(null)
        itineraries_activity_rv_itineraries.visibility = View.VISIBLE
        itineraries_activity_img_lottie_conection.visibility = View.GONE
        itineraries_activity_tv_connection_error.visibility = View.GONE

        hideLoader()
    }

    private fun adapterConstruct(itineraries: List<ItinerariesDTO>) {
        itinerariesAdapter = ItinerariesAdapter(this@SogalItinerariesActivity, itineraries)
        itineraries_activity_rv_itineraries.adapter = itinerariesAdapter
    }
}
