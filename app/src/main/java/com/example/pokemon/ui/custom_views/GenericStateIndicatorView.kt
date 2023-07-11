package com.example.pokemon.ui.custom_views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.pokemon.utils.ResourceState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun <T, E> GenericStateIndicatorView(
    modifier: Modifier,
    state: ResourceState<T, E>,
    buttonBackgroundColor: Color = MaterialTheme.colorScheme.background,
    buttonTextColor: Color = MaterialTheme.colorScheme.onBackground,
    loadingIndicatorColor: Color = MaterialTheme.colorScheme.onBackground,
    operationToPerformOnFailedState: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(modifier = modifier) {

        when (state) {
            is ResourceState.Failed -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = (state.error as Exception).localizedMessage
                            ?: "Some error occurred!",
                        color = buttonTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 15.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonTextColor,
                            contentColor = buttonBackgroundColor,
                        ),
                        onClick = {
                            operationToPerformOnFailedState()
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 8.dp),
                    ) {
                        Text(
                            text = "Retry",
                            color = buttonBackgroundColor,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

            is ResourceState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = loadingIndicatorColor
                )
            }

            is ResourceState.Success -> {
                Column(modifier = modifier, content = content)
            }
        }
    }
}