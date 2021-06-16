package esser.marcelo.busoclock.view.activity.sogal

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.View.*
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.snackbar.Snackbar
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.extensions.hideKeyboard
import esser.marcelo.busoclock.interfaces.GenericLinesAdapterDelegate
import esser.marcelo.busoclock.interfaces.LineMenuDelegate
import esser.marcelo.busoclock.model.BaseLine
import esser.marcelo.busoclock.model.Constants.CB_WAY
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.sogal.LinesDTO
import esser.marcelo.busoclock.view.activity.BaseActivity
import esser.marcelo.busoclock.view.adapter.GenericLinesAdapter
import esser.marcelo.busoclock.view.adapter.LineWaysAdapter
import esser.marcelo.busoclock.view.dialog.LineMenuDialog
import esser.marcelo.busoclock.viewModel.SogalLinesViewModel
import kotlinx.android.synthetic.main.activity_lines.*
import kotlinx.android.synthetic.main.dialog_line_menu.*
import kotlinx.android.synthetic.main.snack_bar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.log


/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class SogalLinesActivity : BaseActivity(R.layout.activity_lines), GenericLinesAdapterDelegate,
    LineMenuDelegate {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private val viewModel: SogalLinesViewModel by viewModel()

    lateinit var lineMenuDialog: LineMenuDialog

    private lateinit var adapter: GenericLinesAdapter
    private val mContext = this

    var lineWay: String = CB_WAY

    override fun observers() {
        linesObserver()
        isFavoriteObserver()
        favoriteLineObserver()
    }

    override fun onInitValues() {
        activity_home_title.text = "Sogal"
        showLoader()
        listeners()
        errorObserver()
    }

    private fun listeners() {
        lav_cancel_search_action.setOnClickListener {
            if (activity_lines_et_search.text.isNotEmpty()) {
                activity_lines_et_search.setText("")
                this.hideKeyboard()
            }
        }
        searchEvent()

        ibBacAction()
    }

    private fun errorObserver() {
        val errorOberver = Observer<String> { message ->
            hideLoader()
            var mMessage = message
            if(isNetworkAvailable(this)){
                mMessage = "Verifique sua conexão com a internet e tente novamente mais tarde"
            }
            showSnackBar(mMessage)
            errorConfig()
        }
        viewModel.error.observe(this, errorOberver)
    }

    private fun favoriteLineObserver() {
        val favoriteLineObserver = Observer<FavoriteLine> { line ->
            showSnackBar("${line.name} salva com sucesso")
        }

        viewModel.mFavoriteLine.observe(mContext, favoriteLineObserver)
    }

    private fun isFavoriteObserver() {
        val isFavoriteObserver = Observer<Boolean> {
            lineMenuDialog.isFavorite = it
            lineMenuDialog.validateImageButton()
        }

        viewModel.isLineFavorite.observe(this, isFavoriteObserver)
    }

    private fun linesObserver() {
        val linesObserver = Observer<List<LinesDTO>> { lines ->
            hideLoader()
            adapterConstruct(lines)
        }
        viewModel.lines.observe(this, linesObserver)
        return
    }

    private fun ibBacAction() {
        lines_activity_img_btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun searchEvent() {
        activity_lines_et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.filterBy(
                    activity_lines_et_search.text.toString()
                )
                searchAnimationControll()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun searchAnimationControll() {
        if (activity_lines_et_search.text.isNotEmpty() && lav_cancel_search_action.progress == 0f) {
            val animate = ValueAnimator.ofFloat(0f, 0.5f)

            animate.addUpdateListener {
                lav_cancel_search_action.progress = it.animatedValue as Float
            }

            animate.duration = 700
            animate.start()
        } else if (activity_lines_et_search.text.isNullOrEmpty() && lav_cancel_search_action.progress == 0.5f) {
            val animate = ValueAnimator.ofFloat(0.5f, 0f)

            animate.addUpdateListener {
                lav_cancel_search_action.progress = it.animatedValue as Float
            }
            animate.duration = 500
            animate.start()
        }
    }

    private fun errorConfig() {
        lines_activity_img_lottie_conection.resumeAnimation()

        lines_activity_img_lottie_conection.setOnClickListener {
            lines_activity_img_lottie_conection.pauseAnimation()
            showLoader()
            viewModel.loadLines()
        }

        lines_activity_img_lottie_conection.visibility = VISIBLE
        lines_activity_tv_connection_error.visibility = VISIBLE
        lines_activity_rv_lines.visibility = INVISIBLE
    }

    private fun successConfig() {

        lines_activity_rv_lines.setOnClickListener(null)
        lines_activity_rv_lines.visibility = VISIBLE
        lines_activity_img_lottie_conection.visibility = GONE
        lines_activity_tv_connection_error.visibility = GONE

        hideLoader()
    }

    private fun adapterConstruct(lines: List<LinesDTO>) {
        adapter = GenericLinesAdapter(lines, this@SogalLinesActivity, this)
        lines_activity_rv_lines.adapter = adapter
        successConfig()
    }

    override fun onItemClickLitener(line: BaseLine) {
        viewModel.saveData(line.code, line.name)

        bottomSheetBehavior = BottomSheetBehavior.from<View>(bottomSheet)

        bottom_sheet_bg.visibility = VISIBLE

        configureBottomSheet(bottomSheetBehavior)

        rvWays.adapter = LineWaysAdapter(this, viewModel.getWaysList(), this)
        bottomSheetBehavior.peekHeight = bottomSheet.height - bottom_sheet_content.height + 100

    }

    private fun configureBottomSheet(bottomSheetBehavior: BottomSheetBehavior<*>) {
        bottom_sheet_bg.setOnClickListener {
            hideBottomSheet()
        }

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    hideBottomSheet()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset == 0f) {
                    hideBottomSheet()
                }
            }
        })

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun hideBottomSheet() {
        bottomSheetBehavior.peekHeight = 0
        bottom_sheet_bg.visibility = GONE
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun goToSchedules() {
        startActivity(Intent(this, SogalSchedulesActivity::class.java))
        hideBottomSheet()
    }

}
