package com.example.pokemon.domain.use_case.get_pokemon

import android.util.Log
import com.example.pokemon.domain.model.repository.PokemonRepository
import com.example.pokemon.domain.model.responses.ModelPokemon
import com.example.pokemon.utils.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetPokemonDataUseCase(private val repository: PokemonRepository) {

    operator fun invoke(): Flow<ResourceState<ModelPokemon, Exception?>> = flow {

        try {
            emit(ResourceState.Loading())
            val pokemonData = repository.getPokemonData()
            emit(ResourceState.Success(pokemonData))
        } catch (e: IOException) {
            Log.e("GetPokemonDataUseCase", e.stackTraceToString())
            emit(ResourceState.Failed(Exception("Couldn't reach server. Is your internet on?")))
        } catch (e: Exception) {
            Log.e("GetPokemonDataUseCase", e.stackTraceToString())
            emit(ResourceState.Failed(e))
        }

    }

}