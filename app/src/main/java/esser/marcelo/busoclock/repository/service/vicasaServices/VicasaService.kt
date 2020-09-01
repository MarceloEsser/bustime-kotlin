package esser.marcelo.busoclock.repository.service.vicasaServices

import esser.marcelo.busoclock.repository.service.wrapper.resource.NetworkBoundResource
import esser.marcelo.busoclock.repository.service.wrapper.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

interface VicasaServiceDelegate {
    fun getLinesFrom(
        destination: String,
        origin: String,
        service: String
    ): Flow<Resource<ResponseBody?>>

    fun getSchedules(lindeId: String): Flow<Resource<ResponseBody?>>
}

open class VicasaService(private val _mApi: IVicasaAPI) : VicasaServiceDelegate {

    override fun getLinesFrom(
        destination: String,
        origin: String,
        service: String
    ): Flow<Resource<ResponseBody?>> {
        return flow {
            NetworkBoundResource(
                collector = this,
                processResponse = { it },
                call = _mApi.postLisnesFrom(destination, origin, service)
            ).build()
        }
    }

    override fun getSchedules(lindeId: String): Flow<Resource<ResponseBody?>> {
        return flow {
            NetworkBoundResource(
                collector = this,
                call = _mApi.getSchedules(lineCode = lindeId),
                processResponse = { it }
            ).build()
        }
    }
}