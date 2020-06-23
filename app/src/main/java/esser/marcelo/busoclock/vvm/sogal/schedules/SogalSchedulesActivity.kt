package esser.marcelo.busoclock.vvm.sogal.schedules

import android.content.Intent
import android.os.Bundle
import android.view.View.*
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.SchedulesAdapter
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.model.schedules.BaseSchedule
import esser.marcelo.busoclock.vvm.BaseActivity
import esser.marcelo.busoclock.vvm.sogal.itineraries.SogalItinerariesActivity
import kotlinx.android.synthetic.main.activity_schedules.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SogalSchedulesActivity : BaseActivity() {

    private val viewModelSogal: SogalSchedulesActivityViewModel by lazy {
        SogalSchedulesActivityViewModel()
    }

    private lateinit var adapter: SchedulesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedules)

        listeners()

        shcedule_activity_tv_line_name.text = LineDAO.lineName
        LineDAO.lineWay?.let {
            tv_line_way.text = it.description
        }

    }

    override fun onStart() {
        super.onStart()
        loadSchedules()
    }

    private fun listeners() {
        btnGoToItineraries()

        bottomNavigationBarListener()

        ibBackAction()
    }

    private fun ibBackAction() {
        schedules_activity_img_btn_back.setOnClickListener {
            finish()
        }
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
                    configureList(viewModelSogal.workingDays)
                    true
                }
                R.id.action_saturday -> {
                    configureList(viewModelSogal.saturdays)
                    true
                }
                R.id.action_sunday -> {
                    configureList(viewModelSogal.sundays)
                    true
                }

                else -> false
            }
        }
    }

    private fun loadSchedules() {
        showLoader()
        GlobalScope.launch {
            delay(400L)
            viewModelSogal.loadSchedulesBy(onSuccess = { schedules ->
                configureList(schedules)
                successConfig()
            }, onError = { errorMessage ->
                errorConfig()
            })
        }
    }

    private fun successConfig() {
        schedules_activity_rv_schedules.setOnClickListener(null)
        schedules_activity_rv_schedules.visibility = VISIBLE
        schedules_activity_img_lottie_conection.visibility = GONE
        schedules_activity_tv_connection_error.visibility = GONE
        hideLoader()
    }

    private fun errorConfig() {
        schedules_activity_img_lottie_conection.resumeAnimation()

        lottieAnimationClick()

        schedules_activity_img_lottie_conection.visibility = VISIBLE
        schedules_activity_tv_connection_error.visibility = VISIBLE
        schedules_activity_rv_schedules.visibility = INVISIBLE

        hideLoader()
    }

    private fun lottieAnimationClick() {
        schedules_activity_img_lottie_conection.setOnClickListener {
            schedules_activity_img_lottie_conection.pauseAnimation()
            loadSchedules()
        }
    }

    private fun configureList(sogalResponse: List<BaseSchedule>?) {
        hideLoader()
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
