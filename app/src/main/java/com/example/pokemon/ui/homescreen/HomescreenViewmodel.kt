package com.example.pokemon.ui.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.domain.model.responses.ModelPokemon
import com.example.pokemon.domain.use_case.get_pokemon.GetPokemonDataUseCase
import com.example.pokemon.utils.ResourceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomescreenViewmodel(
    private val getPokemonDataUseCase: GetPokemonDataUseCase
) : ViewModel() {

    val state = MutableStateFlow<ResourceState<ModelPokemon, Exception?>>(ResourceState.Loading())

    fun onEvent(event: HomescreenEvents) = viewModelScope.launch {
        when (event) {
            HomescreenEvents.FetchPokemon -> {
                if (!state.value.isSuccess()) {
                    getPokemonDataUseCase().onEach {
                        state.value = it
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

}