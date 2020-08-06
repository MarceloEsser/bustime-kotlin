package esser.marcelo.busoclock.vvm.deleteDialog

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import esser.marcelo.busoclock.R

class DeleteDialog : DialogFragment()  {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_question_delete, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val onCreateDialog = super.onCreateDialog(savedInstanceState)
        setupDialog(onCreateDialog)
        onCreateDialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return onCreateDialog
    }

    private fun setupDialog(dialog: Dialog?) {
        dialog?.run {
            window?.attributes?.height = ViewGroup.LayoutParams.WRAP_CONTENT
            window?.attributes?.width = ViewGroup.LayoutParams.MATCH_PARENT
            window?.setGravity(Gravity.CENTER)
        }
    }
}