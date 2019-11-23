package esser.marcelo.busoclock.vvm.lineDialog

import esser.marcelo.busoclock.helper.Constants.BB_WAY
import esser.marcelo.busoclock.helper.Constants.BC_WAY
import esser.marcelo.busoclock.helper.Constants.CB_WAY
import esser.marcelo.busoclock.helper.Constants.CC_WAY
import esser.marcelo.busoclock.model.LineWay

class LineMenuDialogViewModel(val isFromVicasa: Boolean = false) {


    lateinit var selectedWay: LineWay

    fun getWaysList(): ArrayList<LineWay> {
        val waysList: ArrayList<LineWay> = ArrayList()
        waysList.add(LineWay("Selecione um sentido", "none"))
        waysList.add(LineWay("Centro Bairro - CB", CB_WAY))
        waysList.add(LineWay("Bairro Centro - BC", BC_WAY))
        if (isFromVicasa) {
            waysList.add(LineWay("Centro Circular - CC", CC_WAY))
            waysList.add(LineWay("Bairro Circular - BB", BB_WAY))
        }

        return waysList
    }
}