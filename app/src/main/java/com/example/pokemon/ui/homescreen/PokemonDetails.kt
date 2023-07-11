package com.example.pokemon.ui.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.pokemon.domain.model.responses.PokemonAbility
import com.example.pokemon.domain.model.responses.PokemonAttacks
import com.example.pokemon.domain.model.responses.PokemonData
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.palette.PalettePlugin

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun PokemonDetails(
    navigator: DestinationsNavigator,
    data: PokemonData
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val containerColor = remember { mutableStateOf(Color(0xff000000)) }
    val contentColor = remember { mutableStateOf(Color(0xffffffff)) }

    val systemUiController = rememberSystemUiController()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = data.name ?: "Unavailable",
                        color = contentColor.value,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = contentColor.value
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = containerColor.value,
                    scrolledContainerColor = containerColor.value,
                    navigationIconContentColor = contentColor.value,

                    ),
                modifier = Modifier.background(containerColor.value)
            )
        },
        containerColor = containerColor.value,
        contentColor = contentColor.value
    ) {

        LazyColumn(
            modifier = Modifier
                .background(containerColor.value)
                .padding(it)
        ) {

            item {
                CoilImage(imageModel = {
                    data.images?.large
                },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.FillWidth,
                        alignment = Alignment.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    component = rememberImageComponent {
                        +PalettePlugin {

                            val swatch = it.dominantSwatch ?: it.mutedSwatch ?: it.vibrantSwatch
                            ?: it.lightMutedSwatch ?: it.lightVibrantSwatch

                            containerColor.value = Color(swatch?.rgb ?: 0)
                            contentColor.value = Color(swatch?.bodyTextColor ?: 0)

                            systemUiController.setSystemBarsColor(containerColor.value)
                        }
                    })
            }

            item {

                Text(
                    text = buildAnnotatedString {

                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = contentColor.value,
                            )
                        ) {
                            append("Types: ")
                        }

                        withStyle(
                            SpanStyle(
                                color = contentColor.value
                            )
                        ) {
                            data.types?.let {
                                append(it.joinToString(separator = ", "))
                            }

                            appendLine()
                        }

                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = contentColor.value
                            )
                        ) {
                            append("Subtypes: ")
                        }

                        withStyle(
                            SpanStyle(
                                color = contentColor.value
                            )
                        ) {
                            data.subtypes?.let {
                                append(it.joinToString(separator = ", "))
                            }
                            appendLine()
                        }

                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = contentColor.value
                            )
                        ) {
                            append("Level: ")
                        }

                        withStyle(
                            SpanStyle(
                                color = contentColor.value
                            )
                        ) {
                            append(data.level?.toString() ?: "Not available")
                            appendLine()
                        }

                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = contentColor.value
                            )
                        ) {
                            append("HP: ")
                        }

                        withStyle(
                            SpanStyle(
                                color = contentColor.value
                            )
                        ) {
                            append(data.hp?.toInt()?.toString() ?: "Not available")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 18.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
                    textAlign = TextAlign.Center
                )
            }

            data.attacks?.let { attacks ->
                item {
                    Text(
                        text = "Attacks",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, start = 12.dp, end = 12.dp),
                        color = contentColor.value
                    )
                }

                items(attacks) { attack ->
                    PokemonAttack(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        containerColor = containerColor.value,
                        contentColor = contentColor.value,
                        attack = attack
                    )
                }
            }

            data.abilities?.let { abilities ->

                item {
                    Text(
                        text = "Abilities",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, start = 12.dp, end = 12.dp),
                        color = contentColor.value
                    )
                }

                items(abilities) { ability ->
                    PokemonAbilities(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        containerColor = containerColor.value,
                        contentColor = contentColor.value,
                        ability = ability
                    )
                }


            }

            data.weaknesses?.let { weaknesses ->
                item {
                    Text(
                        text = "Weaknesses",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, start = 12.dp, end = 12.dp),
                        color = contentColor.value
                    )
                }

                item {
                    LazyRow(contentPadding = PaddingValues(start = 12.dp, end = 12.dp)) {

                        items(weaknesses) { weakness ->
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        SpanStyle(
                                            fontWeight = FontWeight.Bold,
                                            color = containerColor.value,
                                        )
                                    ) {
                                        append("${weakness.type} ")
                                    }

                                    withStyle(
                                        SpanStyle(
                                            color = containerColor.value
                                        )
                                    ) {
                                        append(weakness.value)
                                    }
                                }, modifier = Modifier
                                    .padding(4.dp)
                                    .background(contentColor.value, shape = ShapeDefaults.Small)
                                    .padding(8.dp)

                            )
                        }

                    }
                }

            }

            data.resistances?.let { resistances ->
                item {
                    Text(
                        text = "Resistances",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, start = 12.dp, end = 12.dp),
                        color = contentColor.value
                    )
                }

                item {
                    LazyRow(contentPadding = PaddingValues(start = 12.dp, end = 12.dp)) {

                        items(resistances) { resistance ->
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        SpanStyle(
                                            fontWeight = FontWeight.Bold,
                                            color = containerColor.value,
                                        )
                                    ) {
                                        append("${resistance.type} ")
                                    }

                                    withStyle(
                                        SpanStyle(
                                            color = containerColor.value
                                        )
                                    ) {
                                        append(resistance.value)
                                    }
                                }, modifier = Modifier
                                    .padding(4.dp)
                                    .background(contentColor.value, shape = ShapeDefaults.Small)
                                    .padding(8.dp)

                            )
                        }

                    }
                }

            }

        }

    }

}

@Composable
fun PokemonAbilities(
    modifier: Modifier,
    containerColor: Color,
    contentColor: Color,
    ability: PokemonAbility
) = OutlinedCard(
    modifier = modifier, colors = CardDefaults.outlinedCardColors(
        containerColor = contentColor,
        contentColor = containerColor
    )
) {

    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = ability.name ?: "Unavailable",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
            color = containerColor
        )


        Text(
            text = buildAnnotatedString {

                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = containerColor,
                    )
                ) {
                    append("Type: ")
                }

                withStyle(
                    SpanStyle(
                        color = containerColor
                    )
                ) {
                    append(ability.type)

                }
            },
            modifier = Modifier.fillMaxWidth(),
            color = containerColor
        )

        Text(
            text = ability.text ?: "Unavailable",
            modifier = Modifier
                .fillMaxWidth(),
            color = containerColor
        )
    }
}

@Composable
fun PokemonAttack(
    modifier: Modifier,
    containerColor: Color,
    contentColor: Color,
    attack: PokemonAttacks,
) = OutlinedCard(
    modifier = modifier, colors = CardDefaults.outlinedCardColors(
        containerColor = contentColor,
        contentColor = containerColor
    )
) {

    Column(modifier = Modifier.padding(12.dp)) {
        Text(
            text = attack.name ?: "Unavailable",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
            color = containerColor
        )

        Text(
            text = attack.text ?: "Unavailable",
            modifier = Modifier.fillMaxWidth(),
            color = containerColor
        )

        Text(
            text = buildAnnotatedString {

                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = containerColor,
                    )
                ) {
                    append("Damage: ")
                }

                withStyle(
                    SpanStyle(
                        color = containerColor
                    )
                ) {
                    append(attack.damage)
                    appendLine()
                }

                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = containerColor,
                    )
                ) {
                    append("Cost: ")
                }

                withStyle(
                    SpanStyle(
                        color = containerColor
                    )
                ) {
                    attack.cost?.let {
                        append(it.joinToString(separator = ", "))
                    }
                    appendLine()
                }

                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = containerColor,
                    )
                ) {
                    append("Converted Energy Cost: ")
                }

                withStyle(
                    SpanStyle(
                        color = containerColor
                    )
                ) {
                    append(attack.convertedEnergyCost?.toString())
                }

            },
            modifier = Modifier.fillMaxWidth(),
            color = containerColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopBar(
    name: String
) = LargeTopAppBar(title = { Text(text = name) })