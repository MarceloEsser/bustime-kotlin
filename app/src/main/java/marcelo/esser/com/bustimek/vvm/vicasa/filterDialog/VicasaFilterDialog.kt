package marcelo.esser.com.bustimek.vvm.vicasa.filterDialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.vicasa_filter_dialog.*
import marcelo.esser.com.bustimek.R

class VicasaFilterDialog(
) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.vicasa_filter_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerArray = ArrayList<String>()
        spinnerArray.add("item1")
        spinnerArray.add("item2")

        val adapter = ArrayAdapter(
            this.activity!!, android.R.layout.simple_spinner_item, spinnerArray
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filter_dialog_sp_county_origin.adapter = adapter

        filter_dialog_btn_cancel.setOnClickListener {
            dismiss()
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val onCreateDialog = super.onCreateDialog(savedInstanceState)
        setupDialog(onCreateDialog)
        onCreateDialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        return onCreateDialog
    }

    override fun onStart() {
        super.onStart()
        setupDialog(dialog)
    }

    fun setupDialog(dialog: Dialog) {
        dialog?.run {
            window.attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT
            window.attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
            window?.setGravity(Gravity.CENTER)
        }
    }
}