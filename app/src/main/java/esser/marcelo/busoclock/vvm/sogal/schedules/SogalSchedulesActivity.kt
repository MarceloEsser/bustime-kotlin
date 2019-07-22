package esser.marcelo.busoclock.vvm.sogal.schedules

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.*
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.SchedulesAdapter
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.helper.ProgressDialogHelper
import esser.marcelo.busoclock.model.sogal.SchedulesDTO
import esser.marcelo.busoclock.vvm.sogal.itineraries.SogalItinerariesActivity
import kotlinx.android.synthetic.main.activity_schedules.*

class SogalSchedulesActivity : AppCompatActivity() {

    private val WORKINGDAY: Int = 1
    private val SATURDAY: Int = 2
    private val SUNDAY: Int = 3

    private val viewModelSogal: SogalSchedulesActivityViewModel by lazy {
        SogalSchedulesActivityViewModel()
    }

    private val progressDialog: ProgressDialogHelper by lazy {
        ProgressDialogHelper(this@SogalSchedulesActivity)
    }

    private lateinit var adapter: SchedulesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedules)

        loadSchedules()

        btnGoToItineraries()

        bottomNavigationBarListener()

        schedules_activity_img_btn_back.setOnClickListener {
            finish()
        }

        shcedule_activity_tv_line_name.text = LineDAO.lineName
    }

    private fun btnGoToItineraries() {
        img_btn_add_itineraries.setOnClickListener {
            val goToItineraries = Intent(this, SogalItinerariesActivity::class.java)
            startActivity(goToItineraries)
        }
    }

    private fun bottomNavigationBarListener() {
        schedules_bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_workingdays -> {
                    adapterConstructor(viewModelSogal.workingDays)
                    true
                }
                R.id.action_saturday -> {
                    adapterConstructor(viewModelSogal.saturdays)
                    true
                }
                R.id.action_sunday -> {
                    adapterConstructor(viewModelSogal.sundays)
                    true
                }

                else -> false
            }
        }
    }

    private fun loadSchedules() {
        progressDialog.showLoader()
        viewModelSogal.loadSchedulesBy(onSuccess = { schedules ->
            adapterConstructor(schedules)
            successConfig()
        }, onError = { errorMessage ->
            onError()
        })
    }

    private fun successConfig() {
        progressDialog.hideLoader()
        schedules_activity_rv_schedules.setOnClickListener(null)
        schedules_activity_rv_schedules.visibility = VISIBLE
        schedules_activity_img_lottie_conection.visibility = GONE
        schedules_activity_tv_connection_error.visibility = GONE
    }

    private fun onError() {
        progressDialog.hideLoader()
        schedules_activity_img_lottie_conection.resumeAnimation()

        lottieAnimationClick()

        schedules_activity_img_lottie_conection.visibility = VISIBLE
        schedules_activity_tv_connection_error.visibility = VISIBLE
        schedules_activity_rv_schedules.visibility = INVISIBLE
    }

    private fun lottieAnimationClick() {
        schedules_activity_img_lottie_conection.setOnClickListener {
            schedules_activity_img_lottie_conection.pauseAnimation()
            loadSchedules()
        }
    }

    private fun adapterConstructor(schedules: List<SchedulesDTO>?) {
        configureList(schedules)
    }


    private fun configureList(sogalResponse: List<SchedulesDTO>?) {
        progressDialog.hideLoader()
        sogalResponse?.let {
            if (it.size > 0) {
                adapter = SchedulesAdapter(this@SogalSchedulesActivity, sogalResponse)
                adapter.notifyDataSetChanged()
                schedules_activity_rv_schedules.adapter = adapter
                tv_schedules_activity_without_items.visibility = GONE
                schedules_activity_rv_schedules.visibility = VISIBLE
            } else {
                tv_schedules_activity_without_items.visibility = VISIBLE
                schedules_activity_rv_schedules.visibility = INVISIBLE
            }
        }
    }
}
