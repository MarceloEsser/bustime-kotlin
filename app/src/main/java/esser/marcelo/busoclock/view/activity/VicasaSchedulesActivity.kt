package esser.marcelo.busoclock.view.activity

import android.os.Bundle
import android.view.View.*
import android.widget.Toast
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.view.adapter.SchedulesAdapter
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.model.schedules.BaseSchedule
import esser.marcelo.busoclock.viewModel.VicasaSchedulesActivityViewModel
import kotlinx.android.synthetic.main.activity_lines.*
import kotlinx.android.synthetic.main.activity_schedules.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class VicasaSchedulesActivity : BaseActivity(R.layout.activity_schedules) {

    private val viewModel: VicasaSchedulesActivityViewModel by lazy {
        VicasaSchedulesActivityViewModel()
    }

    private lateinit var adapter: SchedulesAdapter

    override fun onInitValues() {
        img_btn_add_itineraries.visibility = GONE
        shcedule_activity_tv_line_name.text = LineDAO.lineName

        loadSchedules()

        listeners()

        LineDAO.lineWay?.let {
            shcedule_activity_tv_line_code.text = it.description
        }
    }

    private fun listeners() {
        bottomNavigationBarListener()
        ibBackAction()
    }

    private fun ibBackAction() {
        schedules_activity_img_btn_back.setOnClickListener {
            finish()
        }
    }

    private fun successConfig() {
        schedules_activity_rv_schedules.setOnClickListener(null)
        schedules_activity_rv_schedules.visibility = VISIBLE
        schedules_activity_img_lottie_conection.visibility = GONE
        schedules_activity_tv_connection_error.visibility = GONE

        hideLoader()
    }

    @ExperimentalCoroutinesApi
    private fun errorConfig() {
        schedules_activity_img_lottie_conection.resumeAnimation()

        lottieAnimationClick()

        schedules_activity_img_lottie_conection.visibility = VISIBLE
        schedules_activity_tv_connection_error.visibility = VISIBLE
        schedules_activity_rv_schedules.visibility = INVISIBLE

        hideLoader()
    }

    @ExperimentalCoroutinesApi
    private fun lottieAnimationClick() {
        lines_activity_img_lottie_conection.setOnClickListener {
            lines_activity_img_lottie_conection.pauseAnimation()
            loadSchedules()
        }
    }

    private fun bottomNavigationBarListener() {
        schedules_bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_workingdays -> {
                    configureList(viewModel.workingdaysList)
                    true
                }
                R.id.action_saturday -> {
                    configureList(viewModel.saturdaysList)
                    true
                }
                R.id.action_sunday -> {
                    configureList(viewModel.sundaysList)
                    true
                }

                else -> false
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun loadSchedules() {
        showLoader()

        viewModel.loadSchedules(
            onSuccess = { schedulesList ->
                successConfig()
                configureList(schedulesList)
            }, onError = { errorMessage ->
                errorConfig()
                Toast.makeText(this@VicasaSchedulesActivity, errorMessage, Toast.LENGTH_SHORT)
                    .show()
            })
    }

    private fun configureList(schedulesList: List<BaseSchedule>) {
        if (schedulesList.isEmpty()) {
            tv_schedules_activity_without_items.visibility = VISIBLE
            schedules_activity_rv_schedules.visibility = GONE
        } else {
            schedules_activity_rv_schedules.visibility = VISIBLE
            tv_schedules_activity_without_items.visibility = INVISIBLE
            adapterConstruct(schedulesList)
        }
    }

    private fun adapterConstruct(schedulesList: List<BaseSchedule>) {
        adapter = SchedulesAdapter(this@VicasaSchedulesActivity, schedulesList)
        adapter.notifyDataSetChanged()
        schedules_activity_rv_schedules.adapter = adapter
    }
}
