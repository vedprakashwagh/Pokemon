package com.example.pokemon.ui.homescreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemon.domain.model.responses.ModelPokemon
import com.example.pokemon.domain.model.responses.PokemonData
import com.example.pokemon.ui.homescreen.destinations.PokemonDetailsDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.palette.PalettePlugin

@Composable
fun PokemonList(
    modifier: Modifier,
    data: List<PokemonData>,
    maxHp: Float,
    navigator: DestinationsNavigator
) = LazyColumn(modifier = modifier) {
    items(data) { pokemon ->
        PokemonCard(
            modifier = Modifier.fillMaxWidth(),
            data = pokemon,
            maxHp = maxHp,
            onCardClicked = {
                navigator.navigate(PokemonDetailsDestination(data = pokemon))
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonCard(
    modifier: Modifier,
    data: PokemonData,
    maxHp: Float,
    onCardClicked: () -> Unit
) = Column(modifier = modifier) {

    val containerColor = remember { mutableIntStateOf(0) }
    val contentColor = remember { mutableIntStateOf(0) }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = ShapeDefaults.Medium,
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color(containerColor.value),
            contentColor = Color(contentColor.value)
        ),
        border = BorderStroke(1.dp, Color(contentColor.value)),
        onClick = {
            onCardClicked()
        }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            PokemonDetails(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f), data = data,
                maxHp = maxHp,
                containerColor = Color(containerColor.value),
                contentColor = Color(contentColor.value)
            )

            CoilImage(imageModel = {
                data.images?.large
            },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.CenterEnd
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                component = rememberImageComponent {
                    +PalettePlugin {
                        val swatch = it.dominantSwatch ?: it.mutedSwatch ?: it.vibrantSwatch
                        ?: it.lightMutedSwatch ?: it.lightVibrantSwatch

                        containerColor.value = swatch?.rgb ?: 0
                        contentColor.value = swatch?.bodyTextColor ?: 0
                    }
                })
        }
    }
}

@Composable
fun PokemonDetails(
    modifier: Modifier,
    containerColor: Color,
    contentColor: Color,
    data: PokemonData,
    maxHp: Float
) =
    Column(modifier = modifier) {

        Text(
            text = data.name ?: "Unavailable",
            style = MaterialTheme.typography.headlineLarge,
            color = contentColor
        )
        /*Text(
            text = data.supertype ?: "Unavailable",
            style = MaterialTheme.typography.titleMedium,
            color = contentColor
        )*/

        Text(text = buildAnnotatedString {

            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
            ) {
                append("Supertype: ")
            }

            withStyle(
                SpanStyle(
                    color = contentColor
                )
            ) {

                append(data.supertype)

                appendLine()
            }

            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
            ) {
                append("Subtypes: ")
            }

            withStyle(
                SpanStyle(
                    color = contentColor
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
                    color = contentColor
                )
            ) {
                append("Types: ")
            }

            withStyle(
                SpanStyle(
                    color = contentColor
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
                    color = contentColor
                )
            ) {
                append("Level: ")
            }

            withStyle(
                SpanStyle(
                    color = contentColor
                )
            ) {
                append(data.level?.toString() ?: "Not available")
            }
        })

        HPBar(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .background(
                    contentColor.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp)
                ), currentHp = data.hp ?: 0f, maxHp = maxHp,
            containerColor = containerColor,
            contentColor = contentColor
        )

    }

@Composable
fun HPBar(
    modifier: Modifier,
    containerColor: Color,
    contentColor: Color,
    currentHp: Float,
    maxHp: Float
) = Box(modifier = modifier, contentAlignment = Alignment.CenterStart) {

    Box(
        modifier = Modifier
            .fillMaxWidth(currentHp * (1f / maxHp))
            .height(36.dp)
            .background(
                contentColor.copy(alpha = 0.8f),
                shape = RoundedCornerShape(8.dp)
            )
    )

    Text(
        text = "${currentHp.toInt()} HP",
        style = MaterialTheme.typography.labelSmall,
        color = containerColor,
        modifier = Modifier.align(Alignment.Center)
    )

}
