package marcelo.esser.com.bustimek.vvm.vicasa.filterDialog

import marcelo.esser.com.bustimek.helper.FilterConstants.CACHOEIRINHA
import marcelo.esser.com.bustimek.helper.FilterConstants.CANOAS
import marcelo.esser.com.bustimek.helper.FilterConstants.COMUM
import marcelo.esser.com.bustimek.helper.FilterConstants.EXECUTIVO
import marcelo.esser.com.bustimek.helper.FilterConstants.GRAVATAI
import marcelo.esser.com.bustimek.helper.FilterConstants.INTEGRACAO
import marcelo.esser.com.bustimek.helper.FilterConstants.METROPOLITANA
import marcelo.esser.com.bustimek.helper.FilterConstants.POA
import marcelo.esser.com.bustimek.helper.FilterConstants.ROTAS
import marcelo.esser.com.bustimek.helper.FilterConstants.SELETIVA
import marcelo.esser.com.bustimek.helper.FilterConstants.TODOS
import marcelo.esser.com.bustimek.helper.FilterConstants.URBANA
import marcelo.esser.com.bustimek.model.vicasa.VicasaFilterObject

class VicasaFilterDialogViewModel {

    fun getCountryList(): List<VicasaFilterObject> {
        val countryList = ArrayList<VicasaFilterObject>()
        countryList.add(VicasaFilterObject("Cachoeirinha", CACHOEIRINHA))
        countryList.add(VicasaFilterObject("Canoas", CANOAS))
        countryList.add(VicasaFilterObject("Gravataí", GRAVATAI))
        countryList.add(VicasaFilterObject("Porto Alegre", POA))

        return countryList
    }

    fun getServiceTypeList(): List<VicasaFilterObject> {
        val serviceTypeList = ArrayList<VicasaFilterObject>()
        serviceTypeList.add(VicasaFilterObject("Todos", TODOS))
        serviceTypeList.add(VicasaFilterObject("Comum", COMUM))
        serviceTypeList.add(VicasaFilterObject("Executiva", EXECUTIVO))
        serviceTypeList.add(VicasaFilterObject("Integração", INTEGRACAO))
        serviceTypeList.add(VicasaFilterObject("Metropolitana", METROPOLITANA))
        serviceTypeList.add(VicasaFilterObject("Rotas", ROTAS))
        serviceTypeList.add(VicasaFilterObject("Seletiva", SELETIVA))
        serviceTypeList.add(VicasaFilterObject("Urbana", URBANA))

        return serviceTypeList
    }

}