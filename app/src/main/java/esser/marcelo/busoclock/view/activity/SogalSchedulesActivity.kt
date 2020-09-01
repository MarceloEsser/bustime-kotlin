package esser.marcelo.busoclock.view.activity

import android.content.Intent
import android.view.View.*
import androidx.lifecycle.Observer
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.model.schedules.BaseSchedule
import esser.marcelo.busoclock.view.adapter.SchedulesAdapter
import esser.marcelo.busoclock.viewModel.SogalSchedulesViewModel
import kotlinx.android.synthetic.main.activity_schedules.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SogalSchedulesActivity : BaseActivity(R.layout.activity_schedules) {

    private val viewModelSogal: SogalSchedulesViewModel by viewModel()

    private lateinit var adapter: SchedulesAdapter

    override fun onInitValues() {
        listeners()

        shcedule_activity_tv_line_name.text = LineDAO.lineName

        LineDAO.lineWay?.let {
            shcedule_activity_tv_line_code.text = it.description
        }

        workingdaysObserver()
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
                    configureList(viewModelSogal.workingDays.value)
                    true
                }
                R.id.action_saturday -> {
                    configureList(viewModelSogal.saturdays.value)
                    true
                }
                R.id.action_sunday -> {
                    configureList(viewModelSogal.sundays.value)
                    true
                }

                else -> false
            }
        }
    }

    private fun workingdaysObserver() {
        val workingdays = Observer<List<BaseSchedule>> { workingdays ->
            configureList(workingdays)
            successConfig()
        }

        viewModelSogal.workingDays.observe(this, workingdays)
    }

    private fun successConfig() {
        schedules_activity_rv_schedules.setOnClickListener(null)
        schedules_activity_rv_schedules.visibility = VISIBLE
        schedules_activity_img_lottie_conection.visibility = GONE
        schedules_activity_tv_connection_error.visibility = GONE
        hideLoader()
    }

    //TODO: Review this
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
            workingdaysObserver()
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
