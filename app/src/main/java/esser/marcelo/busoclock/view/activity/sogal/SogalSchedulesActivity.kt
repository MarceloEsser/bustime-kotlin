package esser.marcelo.busoclock.view.activity.sogal

import android.content.Intent
import android.util.DisplayMetrics
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.repository.LineHolder
import esser.marcelo.busoclock.model.schedules.BaseSchedule
import esser.marcelo.busoclock.view.activity.BaseActivity
import esser.marcelo.busoclock.view.adapter.SchedulesAdapter
import esser.marcelo.busoclock.viewModel.sogal.SogalSchedulesViewModel
import kotlinx.android.synthetic.main.newsogalschedules.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class SogalSchedulesActivity : BaseActivity(R.layout.newsogalschedules) {

    private val viewModelSogal: SogalSchedulesViewModel by viewModel()
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private lateinit var adapter: SchedulesAdapter

    override fun observers() {
        workingdaysObserver()
        favoriteLineActions()
        errorObserver()
    }

    override fun onInitValues() {
        showLoader()

        listeners()

        shcedule_activity_tv_line_name.text = LineHolder.lineName

        LineHolder.lineWay?.let {
            shcedule_activity_tv_line_code.text = it.description
        }

    }

    private fun listeners() {
        btnGoToItineraries()

        bottomNavigationBarListener()

        ibBackAction()

        configBottomSheet()
    }

    private fun configBottomSheet() {

        mBottomSheetBehavior = BottomSheetBehavior.from(cl_bottom_sheet)

        val layoutParams = cl_bottom_sheet.layoutParams
        val displayMetrics = DisplayMetrics()

        this.display?.getRealMetrics(displayMetrics)
        layoutParams.height = displayMetrics.heightPixels

        cl_bottom_sheet.layoutParams = layoutParams

        bottomSheetCallBack()
    }

    private fun bottomSheetCallBack() {

        val layoutParams = cl_header_content.layoutParams
        val height = layoutParams.height

        mBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                val intOffSet = abs((1 - slideOffset) * (height + 2)).toInt()
                updateScreenFrom(layoutParams, intOffSet, slideOffset)
            }
        })

    }

    private fun updateScreenFrom(
        layoutParams: ViewGroup.LayoutParams,
        intOffSet: Int,
        slideOffset: Float
    ) {
        layoutParams.height = intOffSet
        img_btn_close.alpha = (1 - (slideOffset))
        lines_activity_img_btn_itinerary.alpha = (1 - (slideOffset))
        tv_title.alpha = (1 - (slideOffset))
        cl_header_content.layoutParams = layoutParams
    }

    private fun ibBackAction() {
        img_btn_close.setOnClickListener {
            finish()
        }
    }

    private fun favoriteLineActions() {
        val isFavoriteObserver = Observer<Boolean> { isFavorite ->
            if (isFavorite)
                img_btn_add_itineraries.setImageResource(R.drawable.ic_favorite)
            else img_btn_add_itineraries.setImageResource(R.drawable.ic_favorite_border)
        }

        viewModelSogal.isLineFavorite.observe(this, isFavoriteObserver)

        img_btn_add_itineraries.setOnClickListener {
            if (viewModelSogal.isLineFavorite.value == false) {
                viewModelSogal.saveLine()
                return@setOnClickListener
            }
            viewModelSogal.deleteLine()
        }
    }

    private fun errorObserver() {
        val errorOberver = Observer<String> { message ->
            hideLoader()
            var mMessage = message
            img_btn_add_itineraries.isEnabled = false
            if(isNetworkAvailable(this)){
                mMessage = "Verifique sua conexão com a internet e tente novamente mais tarde"
            }
            showSnackBar(mMessage)
            errorConfig()
        }
        viewModelSogal.error.observe(this, errorOberver)
    }

    private fun btnGoToItineraries() {
        lines_activity_img_btn_itinerary.setOnClickListener {
            val goToItineraries = Intent(this, SogalItinerariesActivity::class.java)
            startActivity(goToItineraries)
        }
    }

    private fun bottomNavigationBarListener() {
        schedules_bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_workingdays -> {
                    configureList(viewModelSogal.workingdays.value)
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
            img_btn_add_itineraries.isEnabled = true
            configureList(workingdays)
            successConfig()
        }

        viewModelSogal.workingdays.observe(this, workingdays)
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

        schedules_activity_img_lottie_conection.setOnClickListener {
            schedules_activity_img_lottie_conection.pauseAnimation()
            showLoader()
            viewModelSogal.loadSchedules()
        }

        schedules_activity_img_lottie_conection.visibility = VISIBLE
        schedules_activity_tv_connection_error.visibility = VISIBLE
        schedules_activity_rv_schedules.visibility = INVISIBLE
    }


    private fun configureList(sogalResponse: List<BaseSchedule>?) {
        hideLoader()
        sogalResponse?.let {
            if (it.isNotEmpty()) {
                adapter = SchedulesAdapter(this@SogalSchedulesActivity, sogalResponse)
                adapter.notifyDataSetChanged()
                schedules_activity_rv_schedules.adapter = adapter
//                tv_schedules_activity_without_items.visibility = GONE
                schedules_activity_rv_schedules.visibility = VISIBLE
            } else {
//                tv_schedules_activity_without_items.visibility = VISIBLE
                schedules_activity_rv_schedules.visibility = INVISIBLE
            }
        }
    }
}
