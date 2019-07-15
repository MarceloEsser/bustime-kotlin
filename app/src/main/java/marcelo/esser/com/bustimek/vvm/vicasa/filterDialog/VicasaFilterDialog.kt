package marcelo.esser.com.bustimek.vvm.vicasa.filterDialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.vicasa_filter_dialog.*
import marcelo.esser.com.bustimek.R
import marcelo.esser.com.bustimek.interfaces.FilterDialogInteraction
import marcelo.esser.com.bustimek.model.vicasa.Vicasa

class VicasaFilterDialog : DialogFragment() {

    val viewModel: VicasaFilterDialogViewModel by lazy {
        VicasaFilterDialogViewModel()
    }

    lateinit var interaction: FilterDialogInteraction

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.vicasa_filter_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildAdapter()

        filter_dialog_btn_cancel.setOnClickListener {
            dismiss()
        }

        filter_dialog_btn_confirm.setOnClickListener {
            doFilter()
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

    private fun buildAdapter() {
        val adapter = ArrayAdapter(
            this.activity!!, android.R.layout.simple_spinner_item, viewModel.getCountryList()
        )

        val serviceTypeAdapter = ArrayAdapter(
            this.activity!!, android.R.layout.simple_spinner_item, viewModel.getServiceTypeList()
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        serviceTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        filter_dialog_sp_county_origin.adapter = adapter
        filter_dialog_sp_destination_county.adapter = adapter
        filter_dialog_sp_service_type.adapter = serviceTypeAdapter
    }

    private fun doFilter() {

        var countryOrigin = Vicasa()
        var countryDestination = Vicasa()
        var serviceType = Vicasa()

        if (filter_dialog_sp_county_origin.selectedItem != null) {
            countryOrigin = filter_dialog_sp_county_origin.selectedItem as Vicasa
        }

        if (filter_dialog_sp_destination_county.selectedItem != null) {
            countryDestination = filter_dialog_sp_destination_county.selectedItem as Vicasa
        }

        if (filter_dialog_sp_service_type.selectedItem != null) {
            serviceType = filter_dialog_sp_service_type.selectedItem as Vicasa
        }

        interaction.doFilter(countryOrigin.code, countryDestination.code, serviceType.code)

        dismiss()
    }

}