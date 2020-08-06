package esser.marcelo.busoclock.vvm.deleteDialog

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.interfaces.DeleteDelegate
import kotlinx.android.synthetic.main.dialog_question_delete.*

class DeleteDialog(
    val deleteDelegate: DeleteDelegate
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_question_delete, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val onCreateDialog = super.onCreateDialog(savedInstanceState)
        setupDialog(onCreateDialog)
        onCreateDialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return onCreateDialog
    }

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

    private fun setupDialog(dialog: Dialog?) {
        dialog?.run {
            window?.attributes?.height = ViewGroup.LayoutParams.WRAP_CONTENT
            window?.attributes?.width = ViewGroup.LayoutParams.MATCH_PARENT
            window?.setGravity(Gravity.CENTER)
        }
    }

    override fun onStart() {
        super.onStart()
        setupDialog(dialog)
    }
}