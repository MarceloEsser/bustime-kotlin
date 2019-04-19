package marcelo.esser.com.bustimek.vvm.itineraries

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_itineraries.*
import marcelo.esser.com.bustimek.R
import marcelo.esser.com.bustimek.adapter.ItinerariesAdapter

class ItinerariesActivity : AppCompatActivity() {

    private val viewModel: ItinerariesActivityViewModel by lazy {
        ItinerariesActivityViewModel()
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
        viewModel.loadItineraries(
            onSucces = {
                itinerariesAdapter = ItinerariesAdapter(this@ItinerariesActivity, it)
                itineraries_activity_rv_itineraries.adapter = itinerariesAdapter
            }, onError = {
                Toast.makeText(this@ItinerariesActivity, it, Toast.LENGTH_SHORT).show()
            })
    }
}
