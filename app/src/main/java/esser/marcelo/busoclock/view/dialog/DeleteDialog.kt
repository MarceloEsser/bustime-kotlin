package esser.marcelo.busoclock.view.dialog

import android.os.Bundle
import android.view.View
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.interfaces.DeleteDelegate
import kotlinx.android.synthetic.main.dialog_question_delete.*

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class DeleteDialog(
    val deleteDelegate: DeleteDelegate
) : BaseDialog(layout = R.layout.dialog_question_delete) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog_delete_do_the_delete.setOnClickListener {
            deleteDelegate.doDelete()
        }

        dialog_delete_cancel.setOnClickListener {
            dismiss()
        }

        dialog_delete_question_animation.addAnimatorUpdateListener { animator ->
            val progress = (animator.animatedValue as Float * 100).toInt()
            if (progress == 80) {
                dialog_delete_question_animation.pauseAnimation()
            }
        }
    }
}