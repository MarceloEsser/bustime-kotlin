package esser.marcelo.busoclock.viewModel

import esser.marcelo.busoclock.model.Constants.CACHOEIRINHA
import esser.marcelo.busoclock.model.Constants.CANOAS
import esser.marcelo.busoclock.model.Constants.COMUM
import esser.marcelo.busoclock.model.Constants.EXECUTIVO
import esser.marcelo.busoclock.model.Constants.GRAVATAI
import esser.marcelo.busoclock.model.Constants.INTEGRACAO
import esser.marcelo.busoclock.model.Constants.METROPOLITANA
import esser.marcelo.busoclock.model.Constants.POA
import esser.marcelo.busoclock.model.Constants.ROTAS
import esser.marcelo.busoclock.model.Constants.SELETIVA
import esser.marcelo.busoclock.model.Constants.TODOS
import esser.marcelo.busoclock.model.Constants.URBANA
import esser.marcelo.busoclock.model.vicasa.Vicasa

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

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