package esser.marcelo.busoclock.vvm.lineDialog

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import android.view.View.GONE
import android.widget.ArrayAdapter
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.dialog_line_menu.*
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.helper.Constants.CB_WAY
import esser.marcelo.busoclock.vvm.sogal.itineraries.SogalItinerariesActivity
import esser.marcelo.busoclock.vvm.sogal.schedules.SogalSchedulesActivity
import esser.marcelo.busoclock.vvm.vicasa.schedules.VicasaSchedulesActivity

@SuppressLint("ValidFragment")
class LineMenuDialog @SuppressLint("ValidFragment") constructor(
    val isFromVicasa: Boolean
) : DialogFragment() {

    private val viewModel: LineMenuDialogViewModel by lazy {
        LineMenuDialogViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_line_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lineSetup()
        clickEvent()

        validateWhatIsToDo()
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
            if (isFromVicasa) {
                startActivity(Intent(context, VicasaSchedulesActivity::class.java))
            } else {
                startActivity(Intent(context, SogalSchedulesActivity::class.java))
            }
            dismiss()
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

        if (LineDAO.lineWay.equals(CB_WAY))
            tv_line_menu_dialog_line_way.text = "Centro Bairro"
        else tv_line_menu_dialog_line_way.text = "Bairro Centro"
    }

    override fun onStart() {
        super.onStart()
        setupDialog(dialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val onCreateDialog = super.onCreateDialog(savedInstanceState)
        setupDialog(onCreateDialog)
        onCreateDialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        return onCreateDialog
    }

    fun setupDialog(dialog: Dialog) {
        dialog?.run {
            window.attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT
            window.attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
            window?.setGravity(Gravity.CENTER)
        }
    }
}