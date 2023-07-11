package com.example.pokemon.data.repository_impl

import com.example.pokemon.data.remote.PokemonApi
import com.example.pokemon.domain.model.repository.PokemonRepository
import com.example.pokemon.domain.model.responses.ModelPokemon

class PokemonRepositoryImpl(val api: PokemonApi): PokemonRepository {
    override suspend fun getPokemonData(): ModelPokemon {
        return api.getPokemonData()
    }

}