package esser.marcelo.busoclock.view.dialog

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.interfaces.FilterDialogInteraction
import esser.marcelo.busoclock.model.vicasa.Vicasa
import esser.marcelo.busoclock.viewModel.VicasaFilterDialogViewModel
import kotlinx.android.synthetic.main.dialog_vicasa_filter.*

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class VicasaFilterDialog : BaseDialog(R.layout.dialog_vicasa_filter) {

    val viewModel: VicasaFilterDialogViewModel by lazy {
        VicasaFilterDialogViewModel()
    }

    lateinit var interaction: FilterDialogInteraction

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