package marcelo.esser.com.bustimek.vvm.itineraries

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_itineraries.*
import marcelo.esser.com.bustimek.R
import marcelo.esser.com.bustimek.adapter.ItinerariesAdapter
import marcelo.esser.com.bustimek.helper.ProgressDialogHelper
import marcelo.esser.com.bustimek.model.sogal.ItinerariesDTO

class ItinerariesActivity : AppCompatActivity() {

    private val viewModel: ItinerariesActivityViewModel by lazy {
        ItinerariesActivityViewModel()
    }

    private val progressDialog: ProgressDialogHelper by lazy {
        ProgressDialogHelper(this@ItinerariesActivity)
    }

    private lateinit var itinerariesAdapter: ItinerariesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itineraries)

        btnBack()
        loadItineraries()
    }

    private fun btnBack() {
        itineraries_activity_img_btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun loadItineraries() {
        progressDialog.showLoader()
        viewModel.loadItineraries(
            onSucces = {
                adapterConstruct(it)
            }, onError = {
                Toast.makeText(this@ItinerariesActivity, it, Toast.LENGTH_SHORT).show()
            })
    }

    private fun adapterConstruct(it: List<ItinerariesDTO>?) {
        progressDialog.hideLoader()
        itinerariesAdapter = ItinerariesAdapter(this@ItinerariesActivity, it)
        itineraries_activity_rv_itineraries.adapter = itinerariesAdapter
    }
}
