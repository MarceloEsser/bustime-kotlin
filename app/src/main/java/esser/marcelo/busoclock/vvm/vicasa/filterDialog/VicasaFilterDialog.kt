package esser.marcelo.busoclock.vvm.vicasa.filterDialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.*
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.dialog_vicasa_filter.*
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.interfaces.FilterDialogInteraction
import esser.marcelo.busoclock.model.vicasa.Vicasa

class VicasaFilterDialog : androidx.fragment.app.DialogFragment() {

    val viewModel: VicasaFilterDialogViewModel by lazy {
        VicasaFilterDialogViewModel()
    }

    lateinit var interaction: FilterDialogInteraction

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_vicasa_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildAdapter()

        listeners()

    }

    private fun listeners() {
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
        onCreateDialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return onCreateDialog
    }

    override fun onStart() {
        super.onStart()
        setupDialog(dialog)
    }

    fun setupDialog(dialog: Dialog?) {
        dialog?.run {
            window?.attributes?.height = ViewGroup.LayoutParams.WRAP_CONTENT
            window?.attributes?.width = ViewGroup.LayoutParams.MATCH_PARENT
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

        sp_menu_dialog_select_way.adapter = adapter
        filter_dialog_sp_destination_county.adapter = adapter
        filter_dialog_sp_service_type.adapter = serviceTypeAdapter
    }

    private fun doFilter() {

        var countryOrigin = Vicasa()
        var countryDestination = Vicasa()
        var serviceType = Vicasa()

        if (sp_menu_dialog_select_way.selectedItem != null) {
            countryOrigin = sp_menu_dialog_select_way.selectedItem as Vicasa
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