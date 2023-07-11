package com.example.pokemon

import android.app.Application
import android.content.Context
import com.example.pokemon.di.module.apiDependency
import com.example.pokemon.di.module.platformDependency
import com.example.pokemon.di.module.repoDependency
import com.example.pokemon.di.module.useCaseDependency
import com.example.pokemon.di.module.viewmodelDependency
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class PokemonApp : Application(){

    override fun onCreate() {
        super.onCreate()
        initKoin(this)
    }

    private fun initKoin(context: Context) = startKoin {
        androidLogger()
        androidContext(context)

        modules(
            platformDependency,
            apiDependency,
            repoDependency,
            useCaseDependency,
            viewmodelDependency
        )
    }

}