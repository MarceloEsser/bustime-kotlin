package esser.marcelo.busoclock.service.sogalServices

import esser.marcelo.busoclock.helper.BaseUrls.SOGAL_BASE_URL
import esser.marcelo.busoclock.service.NetworkHandler

/**
 * @author Marcelo Esser
 * @since 19/02/19
 */
class SogalService {
    fun sogalSerivce(): ISogalService = NetworkHandler.getInstance(ISogalService::class.java).build(SOGAL_BASE_URL)
}