package marcelo.esser.com.bustimek.service.vicasaServices

import marcelo.esser.com.bustimek.service.NetworkHandler

class VicasaService {
    fun vicasaService(): IVicasaService = NetworkHandler.getInstance(IVicasaService::class.java).build()
}