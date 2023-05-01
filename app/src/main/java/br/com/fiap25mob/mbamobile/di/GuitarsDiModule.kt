package br.com.fiap25mob.mbamobile.di

import br.com.fiap25mob.mbamobile.data.db.GuitarsDB
import br.com.fiap25mob.mbamobile.presentation.guitarlist.GuitarListViewModel
import br.com.fiap25mob.mbamobile.presentation.guitars.GuitarsViewModel
import br.com.fiap25mob.mbamobile.repository.GuitarsRepository
import br.com.fiap25mob.mbamobile.repository.GuitarsRepositoryImpl
import br.com.fiap25mob.mbamobile.repository.local.GuitarsLocalDataSource
import br.com.fiap25mob.mbamobile.repository.local.GuitarsLocalDataSourceImpl
import br.com.fiap25mob.mbamobile.repository.remote.GuitarsRemoteDataSource
import br.com.fiap25mob.mbamobile.repository.remote.GuitarsRemoteDataSourceImpl
import br.com.fiap25mob.mbamobile.utils.FirebaseUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val guitarsDiModule = module {
    viewModel { GuitarListViewModel(repository = get()) }

    viewModel { GuitarsViewModel(repository = get()) }

    factory<GuitarsRepository> {
        GuitarsRepositoryImpl(
            remoteDataSource = get(), localDataSource = get()
        )
    }

    factory<GuitarsRemoteDataSource> {
        GuitarsRemoteDataSourceImpl(
            firebaseConnection = FirebaseUtils(androidContext(), get())
        )
    }

    factory<GuitarsLocalDataSource> {
        GuitarsLocalDataSourceImpl(guitarsDAO = get())
    }

    single { GuitarsDB.getInstance(androidContext()).guitarsDAO }
}