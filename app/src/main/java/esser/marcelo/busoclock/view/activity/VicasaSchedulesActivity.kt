package esser.marcelo.busoclock.view.activity

import android.view.View.*
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.repository.dao.LineDAO
import esser.marcelo.busoclock.model.schedules.BaseSchedule
import esser.marcelo.busoclock.view.adapter.SchedulesAdapter
import esser.marcelo.busoclock.viewModel.VicasaSchedulesViewModel
import kotlinx.android.synthetic.main.activity_lines.*
import kotlinx.android.synthetic.main.activity_schedules.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class VicasaSchedulesActivity : BaseActivity(R.layout.activity_schedules) {

    private val viewModel: VicasaSchedulesViewModel by viewModel()

    private lateinit var adapter: SchedulesAdapter

    override fun onInitValues() {
        showLoader()

        img_btn_add_itineraries.visibility = GONE
        shcedule_activity_tv_line_name.text = LineDAO.lineName

        listeners()

        LineDAO.lineWay?.let {
            shcedule_activity_tv_line_code.text = it.description
        }

        schedulesObservers()
    }

    private fun schedulesObservers() {
        val workingdaysObserver = Observer<List<BaseSchedule>> { workingdays ->
            configureList(workingdays)
            successConfig()
            hideLoader()
        }

        viewModel.workingdays.observe(this, workingdaysObserver)
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

    private fun errorConfig() {
        schedules_activity_img_lottie_conection.resumeAnimation()

        lottieAnimationClick()

        schedules_activity_img_lottie_conection.visibility = VISIBLE
        schedules_activity_tv_connection_error.visibility = VISIBLE
        schedules_activity_rv_schedules.visibility = INVISIBLE

        hideLoader()
    }

    private fun lottieAnimationClick() {
        lines_activity_img_lottie_conection.setOnClickListener {
            lines_activity_img_lottie_conection.pauseAnimation()
            lifecycleScope.launch {
                viewModel.loadSchedules()
            }
        }
    }

    private fun bottomNavigationBarListener() {
        schedules_bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_workingdays -> {
                    configureList(viewModel.workingdays.value)
                    true
                }
                R.id.action_saturday -> {
                    configureList(viewModel.saturdays.value)
                    true
                }
                R.id.action_sunday -> {
                    configureList(viewModel.sundays.value)
                    true
                }

                else -> false
            }
        }
    }

    private fun configureList(schedulesList: List<BaseSchedule>?) {
        if (!schedulesList.isNullOrEmpty()) {
            schedules_activity_rv_schedules.visibility = VISIBLE
            tv_schedules_activity_without_items.visibility = INVISIBLE
            adapterConstruct(schedulesList)
        } else {
            tv_schedules_activity_without_items.visibility = VISIBLE
            schedules_activity_rv_schedules.visibility = GONE
        }
    }

    private fun adapterConstruct(schedulesList: List<BaseSchedule>) {
        adapter = SchedulesAdapter(this@VicasaSchedulesActivity, schedulesList)
        adapter.notifyDataSetChanged()
        schedules_activity_rv_schedules.adapter = adapter
    }
}
