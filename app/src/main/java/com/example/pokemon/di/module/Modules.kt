package com.example.pokemon.di.module


import com.example.pokemon.data.remote.PokemonApi
import com.example.pokemon.data.repository_impl.PokemonRepositoryImpl
import com.example.pokemon.domain.model.repository.PokemonRepository
import com.example.pokemon.domain.use_case.get_pokemon.GetPokemonDataUseCase
import com.example.pokemon.ui.homescreen.HomescreenViewmodel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val platformDependency = module {
    single {
        ktorHttpClient
    }
}


val apiDependency = module {
    single {
        PokemonApi(get())
    }
}

val repoDependency = module {
    single<PokemonRepository> {
        PokemonRepositoryImpl(get())
    }
}

val useCaseDependency = module {
    single {
        GetPokemonDataUseCase(get())
    }
}

val viewmodelDependency = module {
    viewModel {
        HomescreenViewmodel(get())
    }
}