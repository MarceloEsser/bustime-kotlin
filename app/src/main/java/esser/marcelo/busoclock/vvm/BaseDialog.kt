package esser.marcelo.busoclock.vvm

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment

abstract class BaseDialog(
    private val layout: Int,
    private val height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    private val width: Int = ViewGroup.LayoutParams.MATCH_PARENT
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val onCreateDialog = super.onCreateDialog(savedInstanceState)
        setupDialog(onCreateDialog)
        onCreateDialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return onCreateDialog
    }


    private fun setupDialog(dialog: Dialog?) {
        dialog?.run {
            window?.attributes?.height = height
            window?.attributes?.width = width
            window?.setGravity(Gravity.CENTER)
        }
    }

    override fun onStart() {
        super.onStart()
        setupDialog(dialog)
    }

}