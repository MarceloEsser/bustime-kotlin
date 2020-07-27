package esser.marcelo.busoclock.vvm.lineDialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import android.view.View.GONE
import android.widget.AdapterView
import android.widget.Toast
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.spinner.SpinnerDefaultAdapter
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.helper.SaveLineHelper
import esser.marcelo.busoclock.interfaces.SaveLindeDelegate
import esser.marcelo.busoclock.vvm.sogal.itineraries.SogalItinerariesActivity
import esser.marcelo.busoclock.vvm.sogal.schedules.SogalSchedulesActivity
import esser.marcelo.busoclock.vvm.vicasa.schedules.VicasaSchedulesActivity
import kotlinx.android.synthetic.main.dialog_line_menu.*


@SuppressLint("ValidFragment")
class LineMenuDialog @SuppressLint("ValidFragment") constructor(
    val isFromVicasa: Boolean,
    val activityContext: Context,
    var isFavorite: Boolean = false
) : DialogFragment(), SaveLindeDelegate {

    private val viewModel: LineMenuDialogViewModel by lazy {
        LineMenuDialogViewModel(isFromVicasa)
    }

    private val saveLineHelper: SaveLineHelper by lazy {
        SaveLineHelper(this, activityContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_line_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lineSetup()
        clickEvent()
        spinnerAdapterConfig()

        validateWhatIsToDo()

        if (isFavorite) {
            favorite_image_button.setImageResource(R.drawable.ic_favorite)
        } else {
            favorite_image_button.setImageResource(R.drawable.ic_favorite_border)
        }

        favorite_image_button.setOnClickListener {
            if (isFavorite) {
                favorite_image_button.setImageResource(R.drawable.ic_favorite)
                this.isFavorite = false
                saveLineHelper.saveSogalLine(onLineSaved = {

                }, onError = {

                })
            } else {
                favorite_image_button.setImageResource(R.drawable.ic_favorite_border)
                this.isFavorite = true
            }
        }

    }

    private fun spinnerAdapterConfig() {
        val lineWayAdapter = SpinnerDefaultAdapter(activityContext, viewModel.getWaysList())

        sp_menu_dialog_select_way.adapter = lineWayAdapter

        sp_menu_dialog_select_way.onItemSelectedListener = spItemSelectListener()
    }

    private fun spItemSelectListener(): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                viewModel.selectedWay = viewModel.getWaysList()[position]
                LineDAO.lineWay = viewModel.selectedWay
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun validateWhatIsToDo() {
        if (isFromVicasa) {
            btn_line_menu_dialog_itineraries.visibility = GONE
        }
    }

    private fun clickEvent() {
        goToSchedules()
        goToItineraries()
    }

    private fun goToSchedules() {
        btn_line_menu_dialog_schedules.setOnClickListener {
            if (viewModel.selectedWay.way != "none") {
                if (isFromVicasa) {
                    startActivity(Intent(context, VicasaSchedulesActivity::class.java))
                } else {
                    startActivity(Intent(context, SogalSchedulesActivity::class.java))
                }
                dismiss()
            } else {
                Toast.makeText(context, "Por favor selecione um sentido para a linha <3", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToItineraries() {
        btn_line_menu_dialog_itineraries.setOnClickListener {
            startActivity(Intent(context, SogalItinerariesActivity::class.java))
            dismiss()
        }
    }

    private fun lineSetup() {
        tv_line_menu_dialog_line_name.text = LineDAO.lineName
        tv_line_menu_dialog_line_code.text = LineDAO.lineCode
    }

    override fun onStart() {
        super.onStart()
        setupDialog(dialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val onCreateDialog = super.onCreateDialog(savedInstanceState)
        setupDialog(onCreateDialog)
        onCreateDialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return onCreateDialog
    }

    fun setupDialog(dialog: Dialog) {
        dialog?.run {
            window?.attributes?.height = ViewGroup.LayoutParams.WRAP_CONTENT
            window?.attributes?.width = ViewGroup.LayoutParams.MATCH_PARENT
            window?.setGravity(Gravity.CENTER)
        }
    }

    override fun onError(message: String) {

    }

    override fun onSuccess() {
    }
}