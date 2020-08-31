package esser.marcelo.busoclock.view.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.view.adapter.SchedulesAdapter
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.model.schedules.BaseSchedule
import esser.marcelo.busoclock.viewModel.FavoriteSchedulesViewModel
import kotlinx.android.synthetic.main.activity_schedules.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteSchedulesAcitivty : BaseActivity(R.layout.activity_schedules) {

    private val viewModel: FavoriteSchedulesViewModel by viewModel()

    private lateinit var adapter: SchedulesAdapter

    override fun onInitValues() {
        viewModel.fillSchedules()

        setupScreen()
    }

    private fun workingdaysObserver() {
        val workingdayObserver = Observer<List<BaseSchedule>> { schedules ->
            configureList(schedules)
        }

        viewModel.workingDays.observe(this, workingdayObserver)
    }

    private fun setupScreen() {
        shcedule_activity_tv_line_name.text = LineDAO.lineName
        shcedule_activity_tv_line_code.text = LineDAO.lineCode

        schedules_activity_img_btn_back.setOnClickListener {
            onBackPressed()
        }

        workingdaysObserver()

        bottomNavigationBarListener()
    }

    private fun bottomNavigationBarListener() {
        schedules_bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_workingdays -> {
                    configureList(viewModel.workingDays.value)
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

    private fun configureList(schedules: List<BaseSchedule>?) {
        schedules?.let {
            if (it.size > 0) {
                adapter = SchedulesAdapter(this@FavoriteSchedulesAcitivty, schedules)
                adapter.notifyDataSetChanged()
                schedules_activity_rv_schedules.adapter = adapter
                tv_schedules_activity_without_items.visibility = View.GONE
                schedules_activity_rv_schedules.visibility = View.VISIBLE
            } else {
                tv_schedules_activity_without_items.visibility = View.VISIBLE
                schedules_activity_rv_schedules.visibility = View.INVISIBLE
            }
        }
    }
}