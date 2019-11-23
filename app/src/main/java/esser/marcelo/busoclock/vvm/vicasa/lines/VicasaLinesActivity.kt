package esser.marcelo.busoclock.vvm.vicasa.lines

import android.os.Bundle
import android.support.design.card.MaterialCardView
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.VISIBLE
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_lines.*
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.R.menu.vicasa_lines_bottom_navigation_menu
import esser.marcelo.busoclock.adapter.GenericLinesAdapter
import esser.marcelo.busoclock.helper.Constants.BB_WAY
import esser.marcelo.busoclock.helper.Constants.BC_WAY
import esser.marcelo.busoclock.helper.Constants.CB_WAY
import esser.marcelo.busoclock.helper.Constants.CC_WAY
import esser.marcelo.busoclock.helper.ProgressDialogHelper
import esser.marcelo.busoclock.interfaces.FilterDialogInteraction
import esser.marcelo.busoclock.interfaces.GenericLinesAdapterDelegate
import esser.marcelo.busoclock.model.BaseLine
import esser.marcelo.busoclock.model.vicasa.Vicasa
import esser.marcelo.busoclock.vvm.lineDialog.LineMenuDialog
import esser.marcelo.busoclock.vvm.vicasa.filterDialog.VicasaFilterDialog

class VicasaLinesActivity : AppCompatActivity(), FilterDialogInteraction, GenericLinesAdapterDelegate {

    private val viewModel: VicasaLinesActivityViewModel by lazy {
        VicasaLinesActivityViewModel()
    }

    private val progressDialog: ProgressDialogHelper by lazy {
        ProgressDialogHelper(this@VicasaLinesActivity)
    }

    lateinit var menuDialog: LineMenuDialog

    private lateinit var filterDialog: VicasaFilterDialog
    private lateinit var adapter: GenericLinesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lines)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        buildDialog()

        dialogDoFilter()

        listeners()

        //bn_line_way_selector.inflateMenu(vicasa_lines_bottom_navigation_menu)
    }

    private fun listeners() {
        bottomNavigationBarListener()
        searchEvent()
        ibBackAction()
    }

    private fun ibBackAction() {
        lines_activity_img_btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun searchEvent() {
        activity_lines_et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                configureList(viewModel.resultsList.filter {
                    it.name.toLowerCase().contains(activity_lines_et_search.text.toString().toLowerCase())
                            || it.code.toLowerCase().contains(activity_lines_et_search.text.toString().toLowerCase())
                })
            }
        })
    }

    private fun buildDialog() {
        filterDialog = VicasaFilterDialog()
        filterDialog.interaction = this

        filterDialog.show(supportFragmentManager, "teste")
    }

    private fun dialogDoFilter() {
        activity_lines_imgbtn_filter.setOnClickListener {
            filterDialog.show(supportFragmentManager, "teste")
        }
    }

    private fun bottomNavigationBarListener() {
        /*bn_line_way_selector.isSelected = false
        bn_line_way_selector.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_cb -> {
                    viewModel.lineWay = CB_WAY
                    true
                }
                R.id.action_bc -> {
                    viewModel.lineWay = BC_WAY
                    true
                }
                R.id.action_cc -> {
                    viewModel.lineWay = CC_WAY
                    true
                }
                R.id.action_bb -> {
                    viewModel.lineWay = BB_WAY
                    true
                }
                else -> false
            }
        }*/
    }

    private fun configureList(it: List<Vicasa>) {
        adapter = GenericLinesAdapter(it, this@VicasaLinesActivity, this)
        lines_activity_rv_lines.adapter = adapter
        lines_activity_rv_lines.visibility = VISIBLE
    }

    override fun onItemClickLitener(line: BaseLine) {
        viewModel.saveLineData(line.code, line.name)

        menuDialog = LineMenuDialog(true, this@VicasaLinesActivity)
        menuDialog.show(supportFragmentManager, "menuDialog")
    }

    private fun successConfig() {
        lines_activity_rv_lines.setOnClickListener(null)
        lines_activity_rv_lines.visibility = VISIBLE
        lines_activity_img_lottie_conection.visibility = View.GONE
        lines_activity_tv_connection_error.visibility = View.GONE

        progressDialog.hideLoader()
    }

    private fun errorConfig() {
        lines_activity_img_lottie_conection.resumeAnimation()

        lottieAnimationClick()

        lines_activity_img_lottie_conection.visibility = VISIBLE
        lines_activity_tv_connection_error.visibility = VISIBLE
        lines_activity_rv_lines.visibility = View.INVISIBLE

        progressDialog.hideLoader()
    }

    private fun lottieAnimationClick() {
        lines_activity_img_lottie_conection.setOnClickListener {
            lines_activity_img_lottie_conection.pauseAnimation()
            doFilter(viewModel.countryOrigin, viewModel.countryDestination, viewModel.serviceType)
        }
    }

    override fun doFilter(countryOrigin: String, countryDestination: String, serviceType: String) {
        progressDialog.showLoader()

        viewModel.saveFilterData(countryOrigin, countryDestination, serviceType)

        viewModel.loadVicasaLinesBy(
            onSuccess = {
                successConfig()
                configureList(it)
            }, onError = {
                errorConfig()
            })
    }
}
