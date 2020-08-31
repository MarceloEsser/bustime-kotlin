package esser.marcelo.busoclock.dependenciesInjection

import esser.marcelo.busoclock.dao.DaoHelper
import esser.marcelo.busoclock.viewModel.FavoriteLinesViewModel
import esser.marcelo.busoclock.viewModel.FavoriteSchedulesViewModel
import esser.marcelo.busoclock.viewModel.SogalLinesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val roomModule = module {
    single { DaoHelper(context = get()) }
}

private val viewModelModule = module {
    viewModel { SogalLinesViewModel(get()) }
    viewModel { FavoriteLinesViewModel(get()) }
    viewModel { FavoriteSchedulesViewModel(get()) }
}

val appModule = listOf(roomModule, viewModelModule)