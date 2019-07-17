package esser.marcelo.busoclock.vvm.sogal.itineraries

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_itineraries.*
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.ItinerariesAdapter
import esser.marcelo.busoclock.dao.LinesDAO
import esser.marcelo.busoclock.helper.ProgressDialogHelper
import esser.marcelo.busoclock.model.sogal.ItinerariesDTO

class SogalItinerariesActivity : AppCompatActivity() {

    private val viewModelSogal: SogalItinerariesActivityViewModel by lazy {
        SogalItinerariesActivityViewModel()
    }

    private val progressDialog: ProgressDialogHelper by lazy {
        ProgressDialogHelper(this@SogalItinerariesActivity)
    }

    private lateinit var itinerariesAdapter: ItinerariesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itineraries)

        btnBack()
        loadItineraries()

        itineraries_activity_tv_line_name.text = LinesDAO.lineName
    }

    private fun btnBack() {
        itineraries_activity_img_btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun loadItineraries() {
        progressDialog.showLoader()
        viewModelSogal.loadItineraries(
            onSucces = {
                adapterConstruct(it)
            }, onError = {
                Toast.makeText(this@SogalItinerariesActivity, it, Toast.LENGTH_SHORT).show()
            })
    }

    private fun adapterConstruct(it: List<ItinerariesDTO>?) {
        progressDialog.hideLoader()
        itinerariesAdapter = ItinerariesAdapter(this@SogalItinerariesActivity, it)
        itineraries_activity_rv_itineraries.adapter = itinerariesAdapter
    }
}
