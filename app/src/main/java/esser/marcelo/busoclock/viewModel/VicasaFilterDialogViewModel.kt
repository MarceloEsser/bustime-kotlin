package esser.marcelo.busoclock.viewModel

import esser.marcelo.busoclock.helper.Constants.CACHOEIRINHA
import esser.marcelo.busoclock.helper.Constants.CANOAS
import esser.marcelo.busoclock.helper.Constants.COMUM
import esser.marcelo.busoclock.helper.Constants.EXECUTIVO
import esser.marcelo.busoclock.helper.Constants.GRAVATAI
import esser.marcelo.busoclock.helper.Constants.INTEGRACAO
import esser.marcelo.busoclock.helper.Constants.METROPOLITANA
import esser.marcelo.busoclock.helper.Constants.POA
import esser.marcelo.busoclock.helper.Constants.ROTAS
import esser.marcelo.busoclock.helper.Constants.SELETIVA
import esser.marcelo.busoclock.helper.Constants.TODOS
import esser.marcelo.busoclock.helper.Constants.URBANA
import esser.marcelo.busoclock.model.vicasa.Vicasa

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