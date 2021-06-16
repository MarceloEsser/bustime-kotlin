package esser.marcelo.busoclock.view.activity.favorite

import android.content.Intent
import android.view.View
import android.view.View.INVISIBLE
import androidx.lifecycle.Observer
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.view.adapter.SchedulesAdapter
import esser.marcelo.busoclock.repository.LineHolder
import esser.marcelo.busoclock.model.schedules.BaseSchedule
import esser.marcelo.busoclock.view.activity.BaseActivity
import esser.marcelo.busoclock.viewModel.FavoriteSchedulesViewModel
import kotlinx.android.synthetic.main.activity_schedules.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class FavoriteSchedulesAcitivty : BaseActivity(R.layout.activity_schedules) {
    companion object {
        val deletedLineCode = 0
    }

    private val viewModel: FavoriteSchedulesViewModel by viewModel()

    private lateinit var adapter: SchedulesAdapter

    override fun observers() {
        workingDaysObserver()
        favoriteLineActions()
    }

    override fun onInitValues() {
        viewModel.fillSchedules()

        setupScreen()

        lines_activity_img_btn_itinerary.setOnClickListener {
            val goToItineraries = Intent(this, FavoriteItinerariesActivity::class.java)
            startActivity(goToItineraries)
        }
    }

    private fun favoriteLineActions() {
        img_btn_add_itineraries.setOnClickListener {
            viewModel.deleteLine()
            finish()
        }
    }

    private fun workingDaysObserver() {
        val workingDayObserver = Observer<List<BaseSchedule>> { schedules ->
            if(viewModel.isSogal == false) {
                lines_activity_img_btn_itinerary.visibility = INVISIBLE
            }
            configureList(schedules)
        }

        viewModel.workingDays.observe(this, workingDayObserver)
    }

    private fun setupScreen() {
        shcedule_activity_tv_line_name.text = LineHolder.lineName
        shcedule_activity_tv_line_code.text = LineHolder.lineCode

        schedules_activity_img_btn_back.setOnClickListener {
            onBackPressed()
        }

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