package esser.marcelo.busoclock.dependenciesInjection

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

import esser.marcelo.busoclock.repository.dao.DaoHelper
import esser.marcelo.busoclock.repository.dao.DaoHelperDelegate
import esser.marcelo.busoclock.repository.service.NetworkHandler
import esser.marcelo.busoclock.repository.service.callAdapter.CallAdapterFactory
import esser.marcelo.busoclock.repository.service.sogalServices.ISogalAPI
import esser.marcelo.busoclock.repository.service.sogalServices.SogalService
import esser.marcelo.busoclock.repository.service.sogalServices.SogalServiceDelegate
import esser.marcelo.busoclock.repository.service.vicasaServices.IVicasaAPI
import esser.marcelo.busoclock.repository.service.vicasaServices.VicasaService
import esser.marcelo.busoclock.repository.service.vicasaServices.VicasaServiceDelegate
import esser.marcelo.busoclock.viewModel.*
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val roomModule = module {
    single<DaoHelperDelegate> {
        DaoHelper(
            context = get(),
            sogalSchedulesViewModel = get(),
            vicasaSchedulesViewModel = get()
        )
    }
}

private val viewModelModule = module {
    //Sogal viewModels
    viewModel {
        SogalLinesViewModel(
            daoHelper = get(),
            service = get(),
            dispatcher = Dispatchers.IO
        )
    }
    viewModel {
        SogalSchedulesViewModel(
            service = get(),
            dispatcher = Dispatchers.IO
        )
    }
    viewModel {
        SogalItinerariesViewModel(
            service = get(),
            dispatcher = Dispatchers.IO
        )
    }

    //Favorite viewModels
    viewModel {
        FavoriteLinesViewModel(
            daoHelper = get(),
            dispatcher = Dispatchers.IO
        )
    }
    viewModel {
        FavoriteSchedulesViewModel(
            daoHelper = get()
        )
    }

    //Home viewModel
    viewModel {
        HomeViewModel(
            daoHelper = get(),
            dispatcher = Dispatchers.IO
        )
    }

    //VicasaViewModel
    viewModel {
        VicasaLinesViewModel(
            service = get(),
            daoHelper = get(),
            dispatcher = Dispatchers.IO
        )
    }

    viewModel {
        VicasaSchedulesViewModel(
            service = get(),
            dispatcher = Dispatchers.IO
        )
    }
}

private val serviceModule = module {
    single<SogalServiceDelegate> { SogalService(get()) }
    single<VicasaServiceDelegate> { VicasaService(get()) }
}

private val networkModule = module {
    single { retrofit() }

    single { get<Retrofit>().create(ISogalAPI::class.java) }
    single { get<Retrofit>().create(IVicasaAPI::class.java) }
}

private fun retrofit() = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(NetworkHandler.gsonBuilder()))
    .addCallAdapterFactory(CallAdapterFactory())
    .client(NetworkHandler.httpClient())
    .baseUrl("http://sogal.com.br/")
    .build()

val appModule = listOf(roomModule, viewModelModule, networkModule, serviceModule)