package esser.marcelo.busoclock.dependenciesInjection

/**
 * @author Marcelo Esser
 * @since 31/09/20
 */
import esser.marcelo.busoclock.dao.DaoHelper
import esser.marcelo.busoclock.helper.Constants.BaseUrls.SOGAL_BASE_URL
import esser.marcelo.busoclock.service.NetworkHandler
import esser.marcelo.busoclock.service.callAdapter.CallAdapterFactory
import esser.marcelo.busoclock.service.sogalServices.ISogalAPI
import esser.marcelo.busoclock.service.sogalServices.SogalService
import esser.marcelo.busoclock.service.sogalServices.SogalServiceDelegate
import esser.marcelo.busoclock.view.activity.HomeActivity
import esser.marcelo.busoclock.viewModel.*
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

private val roomModule = module {
    single { DaoHelper(context = get()) }
}

private val viewModelModule = module {
    viewModel {
        SogalLinesViewModel(
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
    viewModel {
        FavoriteLinesViewModel(
            daoHelper = get()
        )
    }
    viewModel {
        FavoriteSchedulesViewModel(
            daoHelper = get()
        )
    }

    viewModel {
        HomeViewModel(
            daoHelper = get()
        )
    }
}

private val serviceModule = module {
    single<SogalServiceDelegate> { SogalService(get()) }
}

private val networkModule = module {
    single { retrofit() }

    single { get<Retrofit>().create(ISogalAPI::class.java) }
}

private fun retrofit() = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(NetworkHandler.gsonBuilder()))
    .addCallAdapterFactory(CallAdapterFactory())
    .client(NetworkHandler.httpClient())
    .baseUrl("http://sogal.com.br/")
    .build()

val appModule = listOf(roomModule, viewModelModule, networkModule, serviceModule)