package com.example.pokemon.data.remote

import com.example.pokemon.domain.model.responses.ModelPokemon
import com.example.pokemon.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments

class PokemonApi(private val httpClient: HttpClient) {

    suspend fun getPokemonData(): ModelPokemon = httpClient.get(Constants.Endpoints.ENDPOINT) {

        accept(ContentType.Application.Json)
        url {
            appendPathSegments(Constants.Endpoints.CARDS)
            parameters.append(Constants.Endpoints.PAGE_SIZE, "20")
        }


    }.body()

}