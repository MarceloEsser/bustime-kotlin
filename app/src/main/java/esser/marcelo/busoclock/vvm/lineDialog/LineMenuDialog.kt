package esser.marcelo.busoclock.vvm.lineDialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.adapter.spinner.SpinnerDefaultAdapter
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.interfaces.LineMenuDelegate
import esser.marcelo.busoclock.model.LineWay
import esser.marcelo.busoclock.vvm.BaseDialog
import kotlinx.android.synthetic.main.dialog_line_menu.*

class LineMenuDialog(
    var isFavorite: Boolean = false,
    val delegate: LineMenuDelegate,
    val lineWays: List<LineWay>
) : BaseDialog(layout = R.layout.dialog_line_menu) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lineSetup()
        clickEvent()
        spinnerAdapterConfig()

        if (lineWays.size == 3) {
            btn_line_menu_dialog_itineraries.visibility = GONE
        }

        favoriteButtonEvent()

    }

    private fun favoriteButtonEvent() {

        validateImageButton()

        favorite_image_button.setOnClickListener {
            this.isFavorite = !this.isFavorite

            if (isFavorite) {
                delegate.saveLine()
            } else {
                delegate.removeLine()
            }
        }
    }

    fun validateImageButton() {
        if (isFavorite) {
            favorite_image_button.setImageResource(R.drawable.ic_favorite)
        } else {
            favorite_image_button.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun spinnerAdapterConfig() {
        val lineWayAdapter = SpinnerDefaultAdapter(this.activity!!, lineWays)

        sp_menu_dialog_select_way.adapter = lineWayAdapter

        sp_menu_dialog_select_way.onItemSelectedListener = spItemSelectListener()
    }

    private fun spItemSelectListener(): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                LineDAO.lineWay = lineWays[position]

                if (lineWays[position].way != "none") {
                    favorite_image_button.visibility = VISIBLE
                    btn_line_menu_dialog_schedules.visibility = VISIBLE
                    btn_line_menu_dialog_itineraries.visibility = VISIBLE
                    delegate.findLine()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun clickEvent() {
        goToSchedules()
        goToItineraries()
    }

    private fun goToSchedules() {
        btn_line_menu_dialog_schedules.setOnClickListener {
            if (LineDAO.lineWay?.way != "none") {
                delegate.goToSchedules()
                dismiss()
            }
        }
    }

    private fun goToItineraries() {
        btn_line_menu_dialog_itineraries.setOnClickListener {
            delegate.goToItineraries()
            dismiss()
        }
    }

    private fun lineSetup() {
        tv_line_menu_dialog_line_name.text = LineDAO.lineName
        tv_line_menu_dialog_line_code.text = LineDAO.lineCode
    }
}