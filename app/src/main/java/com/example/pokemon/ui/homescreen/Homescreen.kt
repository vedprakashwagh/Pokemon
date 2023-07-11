package com.example.pokemon.ui.homescreen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemon.domain.model.responses.ModelPokemon
import com.example.pokemon.domain.model.responses.PokemonData
import com.example.pokemon.ui.custom_views.GenericStateIndicatorView
import com.example.pokemon.utils.ResourceState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.asStateFlow
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Destination(start = true)
@Composable
fun Homescreen(navigator: DestinationsNavigator) {


    val viewmodel = koinViewModel<HomescreenViewmodel>()

    val state = viewmodel.state.collectAsState()
    var maxHp by remember {
        mutableStateOf(0f)
    }

    val pokemonList = remember { mutableStateListOf<PokemonData>() }
    val (pokemonToSearch, onSearchTermChanged) = remember { mutableStateOf("") }
    var hpSort by remember { mutableStateOf(Sort.NONE) }
    var levelSort by remember { mutableStateOf(Sort.NONE) }
    var systemUiController = rememberSystemUiController()
    val primaryColor = MaterialTheme.colorScheme.primary


    LaunchedEffect(key1 = Unit, block = {
        viewmodel.onEvent(HomescreenEvents.FetchPokemon)
        systemUiController.setSystemBarsColor(primaryColor)
    })

    LaunchedEffect(key1 = state.value, block = {
        if (state.value.isSuccess()) {
            val data = (state.value as ResourceState.Success).data
            //Finding the max HP in all the Pokemon's
            maxHp = data.data?.maxBy { it.hp ?: 0f }?.hp ?: 0f

            data.data?.let {
                pokemonList.addAll(it)
            }
        }
    })

    LaunchedEffect(key1 = pokemonToSearch, block = {
        if (state.value.isSuccess()) {
            if (pokemonToSearch.isNotEmpty()) {
                pokemonList.removeIf {
                    !(it.name?.lowercase() ?: "").contains(pokemonToSearch.lowercase())
                }
            } else {
                pokemonList.clear()
                val data = (state.value as ResourceState.Success).data
                data.data?.let {
                    pokemonList.addAll(it)
                }
            }
        }
    })

    Column {

        PokeAppBar(
            name = "PokeInfo",
            pokemonToSearch = pokemonToSearch,
            onSearchTermChanged = onSearchTermChanged,
            modifier = Modifier.fillMaxWidth(),
            sortByHP = {
                when (hpSort) {
                    Sort.NONE -> {
                        hpSort = Sort.ASCENDING
                        pokemonList.sortBy {
                            it.hp
                        }
                    }

                    Sort.ASCENDING -> {
                        hpSort = Sort.DESCENDING
                        pokemonList.sortByDescending { it.hp }
                    }

                    Sort.DESCENDING -> {
                        hpSort = Sort.NONE
                        pokemonList.clear()
                        if (state.value.isSuccess()) {
                            val data = (state.value as ResourceState.Success).data
                            data.data?.let {
                                pokemonList.addAll(it)
                            }
                        }
                    }
                }
            },
            sortByLevel = {
                when (levelSort) {
                    Sort.NONE -> {
                        levelSort = Sort.ASCENDING
                        pokemonList.sortBy {
                            it.level ?: 0
                        }
                    }

                    Sort.ASCENDING -> {
                        levelSort = Sort.DESCENDING
                        pokemonList.sortByDescending { it.level ?: 0 }
                    }

                    Sort.DESCENDING -> {
                        levelSort = Sort.NONE
                        pokemonList.clear()
                        if (state.value.isSuccess()) {
                            val data = (state.value as ResourceState.Success).data
                            data.data?.let {
                                pokemonList.addAll(it)
                            }
                        }
                    }
                }
            }
        )

        GenericStateIndicatorView(
            modifier = Modifier
                .fillMaxSize(),
            state = state.value,
            operationToPerformOnFailedState = {
                viewmodel.onEvent(HomescreenEvents.FetchPokemon)
            }) {

            PokemonList(
                modifier = Modifier.fillMaxSize(),
                data = pokemonList,
                maxHp = maxHp,
                navigator = navigator
            )

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokeAppBar(
    name: String,
    pokemonToSearch: String,
    onSearchTermChanged: (String) -> Unit,
    sortByHP: () -> Unit,
    sortByLevel: () -> Unit,
    modifier: Modifier
) =
    Box(modifier = modifier) {

        var shouldShowSearch by remember { mutableStateOf(false) }

        //AnimatedVisibility(visible = !shouldShowSearch) {
        TopAppBar(
            title = {
                Text(text = name)
            }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            actions = {
                IconButton(onClick = {
                    shouldShowSearch = true
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Pokemon",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }


                IconButton(onClick = { sortByHP() }) {
                    Icon(
                        imageVector = Icons.Default.Healing,
                        contentDescription = "Sort by HP",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

                IconButton(onClick = { sortByLevel() }) {
                    Icon(
                        imageVector = Icons.Default.Numbers,
                        contentDescription = "Sort by Level",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

            },
            modifier = Modifier.fillMaxWidth()
        )
        //}

        AnimatedVisibility(shouldShowSearch) {

            TextField(
                value = pokemonToSearch,
                onValueChange = onSearchTermChanged,
                textStyle = TextStyle(
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Filled.Close,
                        null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.clickable {
                            shouldShowSearch = false
                            onSearchTermChanged("")
                        })
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),
                placeholder = {
                    Text(
                        text = "Search",
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.DarkGray,
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                keyboardActions = KeyboardActions(onDone = {
                    shouldShowSearch = false
                }),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )

        }
    }

enum class Sort {
    NONE,
    ASCENDING,
    DESCENDING
}