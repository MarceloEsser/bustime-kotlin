package esser.marcelo.busoclock.vvm.sogal.lines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View.*
import android.view.WindowManager
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.GenericLinesAdapter
import esser.marcelo.busoclock.adapter.GenericLinesAdapter2
import esser.marcelo.busoclock.box.Bus
import esser.marcelo.busoclock.helper.Constants.BC_WAY
import esser.marcelo.busoclock.helper.Constants.CB_WAY
import esser.marcelo.busoclock.helper.ProgressDialogHelper
import esser.marcelo.busoclock.interfaces.GenericLinesAdapterDelegate
import esser.marcelo.busoclock.model.BaseLine
import esser.marcelo.busoclock.model.boxModels.BoxLine
import esser.marcelo.busoclock.model.sogal.LinesDTO
import esser.marcelo.busoclock.vvm.lineDialog.LineMenuDialog
import kotlinx.android.synthetic.main.activity_lines.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SogalLinesActivity : AppCompatActivity() {
    private val viewModel: SogalLinesActivityViewModel by lazy {
        SogalLinesActivityViewModel()
    }

    private val progressDialog: ProgressDialogHelper by lazy {
        ProgressDialogHelper(this@SogalLinesActivity)
    }

    private val lineMenuDialog: LineMenuDialog by lazy {
        LineMenuDialog(false)
    }

    private lateinit var adapter: GenericLinesAdapter2
    var lineWay: String = CB_WAY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lines)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        activity_lines_imgbtn_filter.visibility = GONE

        listeners()
    }

    override fun onStart() {
        super.onStart()
        loadLines()
    }

    private fun listeners() {
        bottomNavigationBarListener()

        searchEvent()

        ibBacAction()
    }

    private fun bottomNavigationBarListener() {
        lines_bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_cb -> {
                    setTitle(R.string.cb_way)
                    lineWay = CB_WAY
                    true
                }
                R.id.action_bc -> {
                    setTitle(R.string.bc_way)
                    lineWay = BC_WAY
                    true
                }
                else -> false
            }
        }
    }

    private fun ibBacAction() {
        lines_activity_img_btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun searchEvent() {
        activity_lines_et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) {
                    adapterConstruct(viewModel.buildFilter {
                        this.name.toLowerCase().contains(activity_lines_et_search.text.toString().toLowerCase())
                                || this.code.toLowerCase().contains(activity_lines_et_search.text.toString().toLowerCase())
                    })
                } else {
                    adapterConstruct(viewModel.buildList())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        })
    }

    private fun loadLines() {
        progressDialog.showLoader()
        GlobalScope.launch {
            delay(400L)
            viewModel.loadSogalLines(
                onSucces = {
                    adapterConstruct(it)
                }, onError = {
                    progressDialog.hideLoader()
                    errorConfig()
                })
        }
    }

    private fun errorConfig() {
        lines_activity_img_lottie_conection.resumeAnimation()

        lines_activity_img_lottie_conection.setOnClickListener {
            lines_activity_img_lottie_conection.pauseAnimation()
            loadLines()
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

        progressDialog.hideLoader()
    }

    private fun adapterConstruct(it: List<GenericLinesAdapter2.Base>) {
        adapter = GenericLinesAdapter2(it, this)
        adapter.onItemClickListener = this::onItemClickListener
        adapter.onFavoriteClickListener = this::onFavoriteClickListener
        lines_activity_rv_lines.adapter = adapter
        successConfig()
    }

    private fun onItemClickListener(line: GenericLinesAdapter2.Line) {
        viewModel.saveData(line.code, line.name, lineWay)
        lineMenuDialog.show(supportFragmentManager, "lineMenuDialog")
    }

    private fun onFavoriteClickListener (line: GenericLinesAdapter2.Line, delete: Boolean) {
        if (delete) {
            viewModel.deleteFavorite(line) {
                adapterConstruct(viewModel.buildList())
            }
        } else {
            viewModel.saveFavorite(lineWay, line) {
                adapterConstruct(viewModel.buildList())
            }
        }
    }
}
