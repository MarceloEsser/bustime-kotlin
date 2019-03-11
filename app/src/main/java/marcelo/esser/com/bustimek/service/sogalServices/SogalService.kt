package marcelo.esser.com.bustimek.service.sogalServices

import marcelo.esser.com.bustimek.service.NetworkHandler

/**
 * @author Marcelo Esser
 * @since 19/02/19
 */
class SogalService {
    fun sogalSerivce(): ISogalService = NetworkHandler.getInstance(ISogalService::class.java).build()
}