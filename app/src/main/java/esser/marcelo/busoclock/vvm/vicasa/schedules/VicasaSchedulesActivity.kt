package esser.marcelo.busoclock.vvm.vicasa.schedules

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.*
import android.widget.Toast
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.SchedulesAdapter
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.helper.ProgressDialogHelper
import esser.marcelo.busoclock.model.sogal.SchedulesDTO
import esser.marcelo.busoclock.vvm.sogal.itineraries.SogalItinerariesActivity
import kotlinx.android.synthetic.main.activity_schedules.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class VicasaSchedulesActivity : AppCompatActivity() {

    private val viewModel: VicasaSchedulesActivityViewModel by lazy {
        VicasaSchedulesActivityViewModel()
    }

    private val progressDialog: ProgressDialogHelper by lazy {
        ProgressDialogHelper(this@VicasaSchedulesActivity)
    }

    private lateinit var adapter: SchedulesAdapter

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedules)
        img_btn_add_itineraries.visibility = GONE

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
                    progressDialog.showLoader()
                    adapterConstructor(viewModel.workingdaysList)
                    true
                }
                R.id.action_saturday -> {
                    progressDialog.showLoader()
                    adapterConstructor(viewModel.saturdaysList)
                    true
                }
                R.id.action_sunday -> {
                    progressDialog.showLoader()
                    adapterConstructor(viewModel.sundaysList)
                    true
                }

                else -> false
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun loadSchedules() {
        progressDialog.showLoader()

        viewModel.loadSchedules(
            onSuccess = { schedulesList ->
                adapterConstructor(schedulesList)
            }, onError = { errorMessage ->
                progressDialog.hideLoader()
                Toast.makeText(this@VicasaSchedulesActivity, errorMessage, Toast.LENGTH_SHORT).show()
            })
    }

    private fun adapterConstructor(schedulesList: List<SchedulesDTO>) {
        progressDialog.hideLoader()
        if (schedulesList.size == 0) {
            tv_schedules_activity_without_items.visibility = VISIBLE
            schedules_activity_rv_schedules.visibility = GONE
        } else {
            schedules_activity_rv_schedules.visibility = VISIBLE
            tv_schedules_activity_without_items.visibility = INVISIBLE
            configureList(schedulesList)
        }
    }

    private fun configureList(schedulesList: List<SchedulesDTO>) {
        adapter = SchedulesAdapter(this@VicasaSchedulesActivity, schedulesList)
        adapter.notifyDataSetChanged()
        schedules_activity_rv_schedules.adapter = adapter
    }
}
