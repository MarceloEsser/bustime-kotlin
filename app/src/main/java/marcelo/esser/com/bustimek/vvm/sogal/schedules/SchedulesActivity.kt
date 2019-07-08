package marcelo.esser.com.bustimek.vvm.sogal.schedules

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_schedules.*
import marcelo.esser.com.bustimek.R
import marcelo.esser.com.bustimek.adapter.SchedulesAdapter
import marcelo.esser.com.bustimek.helper.ProgressDialogHelper
import marcelo.esser.com.bustimek.model.sogal.SchedulesDTO
import marcelo.esser.com.bustimek.model.sogal.SogalResponse
import marcelo.esser.com.bustimek.vvm.sogal.itineraries.ItinerariesActivity

class SchedulesActivity : AppCompatActivity() {

    private val WORKINGDAY: Int = 1
    private val SATURDAY: Int = 2
    private val SUNDAY: Int = 3

    private val viewModel: SchedulesActivityViewModel by lazy {
        SchedulesActivityViewModel()
    }

    private val progressDialog: ProgressDialogHelper by lazy {
        ProgressDialogHelper(this@SchedulesActivity)
    }

    private lateinit var adapter: SchedulesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedules)

        loadSchedules(WORKINGDAY)

        btnGoToItineraries()

        bottomNavigationBarListener()
        img_btn_add_remove_line.setOnClickListener {
            viewModel.addOrRemoveLine()
        }
    }

    private fun btnGoToItineraries() {
        img_btn_add_itineraries.setOnClickListener {
            val goToItineraries = Intent(this, ItinerariesActivity::class.java)
            startActivity(goToItineraries)
        }
    }

    private fun bottomNavigationBarListener() {
        schedules_bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_workingdays -> {
                    loadSchedules(WORKINGDAY)
                    true
                }
                R.id.action_saturday -> {
                    loadSchedules(SATURDAY)
                    true
                }
                R.id.action_sunday -> {
                    loadSchedules(SUNDAY)
                    true
                }

                else -> false
            }
        }
    }

    private fun loadSchedules(scheduleDay: Int) {
        progressDialog.showLoader()
        viewModel.loadSchedulesBy(onSuccess = { sogalResponse ->
            adapterConstructor(sogalResponse, scheduleDay)
        }, onError = { errorMessage ->

        })
    }

    private fun adapterConstructor(sogalResponse: SogalResponse, scheduleDay: Int) {
        when (scheduleDay) {
            WORKINGDAY -> {
                configureList(sogalResponse.workingDays)
            }
            SATURDAY -> {
                configureList(sogalResponse.saturdays)
            }
            SUNDAY -> {
                configureList(sogalResponse.sundays)
            }
        }
    }

    private fun configureList(sogalResponse: List<SchedulesDTO>?) {
        progressDialog.hideLoader()
        sogalResponse?.let {
            adapter = SchedulesAdapter(this@SchedulesActivity, sogalResponse)
            adapter.notifyDataSetChanged()
            schedules_activity_rv_schedules.adapter = adapter
        }
    }
}
