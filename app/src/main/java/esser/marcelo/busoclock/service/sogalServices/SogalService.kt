package esser.marcelo.busoclock.service.sogalServices

import esser.marcelo.busoclock.service.wrapper.resource.Resource
import esser.marcelo.busoclock.model.sogal.LinesDTO
import esser.marcelo.busoclock.model.sogal.SogalResponse
import esser.marcelo.busoclock.service.wrapper.resource.NetworkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

interface SogalServiceDelegate {
    suspend fun getSchedules(lineWay: String, lineCode: String): Flow<Resource<SogalResponse?>>
    suspend fun getLines(): Flow<Resource<List<LinesDTO>?>>
    suspend fun getSogalItineraries(lineCode: String): Flow<Resource<LinesDTO?>>
}

class SogalService(
    private val _mApi: ISogalAPI
) : SogalServiceDelegate {

    private val SEARCH_LINES: String = "buscaLinhas"
    private val SEARCH_ITINERARIES: String = "buscaItinerarios"

    override suspend fun getSchedules(
        lineWay: String,
        lineCode: String
    ): Flow<Resource<SogalResponse?>> {
        return flow {
            NetworkBoundResource(
                collector = this,
                call = _mApi.getSogalSchedulesBy(lineWay, lineCode),
                processResponse = { it }
            ).build()
        }
    }

    override suspend fun getLines(): Flow<Resource<List<LinesDTO>?>> {
        return flow {
            NetworkBoundResource(
                collector = this,
                processResponse = { it },
                call = _mApi.getSogalList(SEARCH_LINES)
            ).build()
        }
    }

    override suspend fun getSogalItineraries(lineCode: String): Flow<Resource<LinesDTO?>> {
        return flow {
            NetworkBoundResource(
                collector = this,
                processResponse = { it },
                call = _mApi.getSogalItineraries(SEARCH_ITINERARIES, lineCode)
            ).build()
        }
    }
}