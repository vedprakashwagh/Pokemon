package com.example.pokemon.domain.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//TODO Add @SerialName annotations to all the variables

@Serializable
data class ModelPokemon(
    val data: List<PokemonData>? = null,
    val page: Int? = null,
    val pageSize: Int? = null,
    val count: Int? = null,
    val totalCount: Int?

)

@Serializable
data class PokemonData(
    val id: String? = null,
    val name: String? = null,
    val supertype: String? = null,
    val subtypes: List<String>? = null,
    val hp: Float? = null,
    val types: List<String>? = null,
    val evolvesFrom: String? = null,
    val attacks: List<PokemonAttacks>? = null,
    val weaknesses: List<PokemonWeaknesses>? = null,
    val resistances: List<PokemonResistances>? = null,
    val retreatCost: List<String>? = null,
    val convertedRetreatCost: Float? = null,
    val set: PokemonSet? = null,
    val number: String? = null,
    val artist: String? = null,
    val rarity: String? = null,
    val flavorText: String? = null,
    val nationalPokedexNumbers: List<Int>? = null,
    val legalities: Legalities? = null,
    val images: PokemonImages? = null,
    val tcgplayer: PokemonTCGPlayer? = null,
    val cardmarket: PokemonCardMarket?,
    val level: Int? = null,
    val abilities: List<PokemonAbility>? = null
)

@Serializable
data class PokemonAbility(
    val name: String? = null,
    val text: String? = null,
    val type: String? = null
)

@Serializable
data class PokemonCardMarket(
    val url: String? = null,
    val updatedAt: String? = null,
    val prices: PokemonCardPrices?
)

@Serializable
data class PokemonCardPrices(
    val averageSellPrice: Float? = null,
    val lowPrice: Float? = null,
    val trendPrice: Float? = null,
    val germanProLow: Float? = null,
    val suggestedPrice: Float? = null,
    val reverseHoloSell: Float? = null,
    val reverseHoloLow: Float? = null,
    val reverseHoloTrend: Float? = null,
    val lowPriceExPlus: Float? = null,
    val avg1: Float? = null,
    val avg7: Float? = null,
    val avg30: Float? = null,
    val reverseHoloAvg1: Float? = null,
    val reverseHoloAvg7: Float? = null,
    val reverseHoloAvg30: Float? = null,
)

@Serializable
data class PokemonTCGPlayer(
    val url: String? = null,
    val updatedAt: String? = null,
    val prices: Map<String, PokemonPrices>? = null
)

@Serializable
data class PokemonPrices(
    val low: Float? = null,
    val mid: Float? = null,
    val high: Float? = null,
    val market: Float? = null,
    val directLow: Float? = null
)

@Serializable
data class PokemonImages(
    val small: String? = null,
    val large: String? = null
)

@Serializable
data class Legalities(
    val unlimited: Expanded? = null,
    val expanded: Expanded? = null
)

@Serializable
enum class Expanded(val value: String) {
    @SerialName("Legal")
    Legal("Legal");
}

@Serializable
data class PokemonAttacks(
    val name: String? = null,
    val cost: List<String>? = null,
    val convertedEnergyCost: Int? = null,
    val damage: String? = null,
    val text: String? = null
)

@Serializable
data class PokemonWeaknesses(
    val type: String? = null,
    val value: String? = null
)

@Serializable
data class PokemonResistances(
    val type: String? = null,
    val value: String? = null
)

@Serializable
data class PokemonSet(
    val id: String? = null,
    val name: String? = null,
    val series: String? = null,
    val printedTotal: Int? = null,
    val total: Int? = null,
    val legalities: Map<String, String>? = null,
    val ptcgoCode: String? = null,
    val releaseDate: String? = null,
    val updatedAt: String? = null,
    val images: PokemonSetImages? = null,
)

@Serializable
data class PokemonSetImages(
    val symbol: String? = null,
    val logo: String? = null
)
