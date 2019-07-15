package marcelo.esser.com.bustimek.vvm.vicasa.filterDialog

import marcelo.esser.com.bustimek.helper.Constants.CACHOEIRINHA
import marcelo.esser.com.bustimek.helper.Constants.CANOAS
import marcelo.esser.com.bustimek.helper.Constants.COMUM
import marcelo.esser.com.bustimek.helper.Constants.EXECUTIVO
import marcelo.esser.com.bustimek.helper.Constants.GRAVATAI
import marcelo.esser.com.bustimek.helper.Constants.INTEGRACAO
import marcelo.esser.com.bustimek.helper.Constants.METROPOLITANA
import marcelo.esser.com.bustimek.helper.Constants.POA
import marcelo.esser.com.bustimek.helper.Constants.ROTAS
import marcelo.esser.com.bustimek.helper.Constants.SELETIVA
import marcelo.esser.com.bustimek.helper.Constants.TODOS
import marcelo.esser.com.bustimek.helper.Constants.URBANA
import marcelo.esser.com.bustimek.model.vicasa.Vicasa

class VicasaFilterDialogViewModel {

    fun getCountryList(): List<Vicasa> {
        val countryList = ArrayList<Vicasa>()
        countryList.add(Vicasa("Cachoeirinha", CACHOEIRINHA))
        countryList.add(Vicasa("Canoas", CANOAS))
        countryList.add(Vicasa("Gravataí", GRAVATAI))
        countryList.add(Vicasa("Porto Alegre", POA))

        return countryList
    }

    fun getServiceTypeList(): List<Vicasa> {
        val serviceTypeList = ArrayList<Vicasa>()
        serviceTypeList.add(Vicasa("Todos", TODOS))
        serviceTypeList.add(Vicasa("Comum", COMUM))
        serviceTypeList.add(Vicasa("Executiva", EXECUTIVO))
        serviceTypeList.add(Vicasa("Integração", INTEGRACAO))
        serviceTypeList.add(Vicasa("Metropolitana", METROPOLITANA))
        serviceTypeList.add(Vicasa("Rotas", ROTAS))
        serviceTypeList.add(Vicasa("Seletiva", SELETIVA))
        serviceTypeList.add(Vicasa("Urbana", URBANA))

        return serviceTypeList
    }

}