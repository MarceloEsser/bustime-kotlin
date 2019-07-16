package marcelo.esser.com.bustimek.vvm.lineDialog

import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import kotlinx.android.synthetic.main.dialog_line_menu.*
import marcelo.esser.com.bustimek.R
import marcelo.esser.com.bustimek.dao.LinesDAO
import marcelo.esser.com.bustimek.helper.Constants.CB_WAY
import marcelo.esser.com.bustimek.vvm.sogal.itineraries.SogalItinerariesActivity
import marcelo.esser.com.bustimek.vvm.sogal.schedules.SogalSchedulesActivity

class LineMenuDialog : DialogFragment() {

    var control = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_line_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lineSetup()

        lottieAnimationSetup()

        clickEvent()
    }

    private fun clickEvent() {
        goToSchedules()
        goToItineraries()
    }

    private fun goToSchedules() {
        btn_line_menu_dialog_schedules.setOnClickListener {
            startActivity(Intent(context, SogalSchedulesActivity::class.java))
            dismiss()
        }
    }

    private fun goToItineraries() {
        btn_line_menu_dialog_itineraries.setOnClickListener {
            startActivity(Intent(context, SogalItinerariesActivity::class.java))
            dismiss()
        }
    }

    private fun lottieAnimationSetup() {
        lottie_animation_view_radio_button.setOnClickListener {
            control = !control
            val animator = if (control) ValueAnimator.ofFloat(0f, 0.5f) else ValueAnimator.ofFloat(0.5f, 1f)
            animator.addUpdateListener { animation ->
                lottie_animation_view_radio_button.progress = animation.animatedValue as Float
            }
            animator.duration = 700
            animator.start()
        }
    }

    private fun lineSetup() {
        tv_line_menu_dialog_line_name.text = LinesDAO.lineName
        tv_line_menu_dialog_line_code.text = LinesDAO.lineCode

        if (LinesDAO.lineWay.equals(CB_WAY))
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