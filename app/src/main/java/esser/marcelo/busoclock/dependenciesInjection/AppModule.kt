package esser.marcelo.busoclock.dependenciesInjection

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

import androidx.room.Room
import esser.marcelo.busoclock.repository.dao.helper.DaoHelper
import esser.marcelo.busoclock.repository.dao.database.AppDatabase
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

private val daoHelperModule = module {
    single {
        DaoHelper(
            context = get(),
            database = get(),
            sogalItinerariesViewModel = get(),
            sogalSchedulesViewModel = null,
            vicasaSchedulesViewModel = null,
        )
    }
}

private val viewModelModule = module {
    //Sogal viewModels
    viewModel {
        SogalLinesViewModel(
            service = get(),
            dispatcher = Dispatchers.IO
        )
    }
    viewModel {
        SogalSchedulesViewModel(
            daoHelper = get(),
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
            dao = get(),
            dispatcher = Dispatchers.IO
        )
    }
    viewModel {
        FavoriteSchedulesViewModel(
            dao = get(),
            dispatcher = Dispatchers.IO
        )
    }
    viewModel {
        FavoriteItinerariesViewModel(
            dao = get(),
            dispatcher = Dispatchers.IO
        )
    }

    //Home viewModel
    viewModel {
        HomeViewModel(
            dao = get(),
            dispatcher = Dispatchers.IO
        )
    }

    //VicasaViewModel
    viewModel {
        VicasaLinesViewModel(
            service = get(),
            dao = get(),
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

val dbModule = module {
    single<AppDatabase> { Room.databaseBuilder(get(), AppDatabase::class.java, "bustime").build() }
    single { get<AppDatabase>().busTimeDao() }
}

private fun retrofit() = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(NetworkHandler.gsonBuilder()))
    .addCallAdapterFactory(CallAdapterFactory())
    .client(NetworkHandler.httpClient())
    .baseUrl("http://sogal.com.br/")
    .build()

val appModule = listOf(daoHelperModule, viewModelModule, networkModule, serviceModule, dbModule)