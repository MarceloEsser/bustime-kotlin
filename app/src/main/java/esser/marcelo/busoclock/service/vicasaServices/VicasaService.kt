package esser.marcelo.busoclock.service.vicasaServices

import esser.marcelo.busoclock.helper.Constants.BaseUrls.VICASA_BASE_URL
import esser.marcelo.busoclock.service.NetworkHandler

interface VicasaServiceDelegate {

}

open class VicasaService {
    fun vicasaService(): IVicasaService = NetworkHandler.getInstance(IVicasaService::class.java).build(VICASA_BASE_URL)
}