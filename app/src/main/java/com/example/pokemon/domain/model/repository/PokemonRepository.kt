package com.example.pokemon.domain.model.repository

import com.example.pokemon.domain.model.responses.ModelPokemon

interface PokemonRepository {
    suspend fun getPokemonData(): ModelPokemon
}