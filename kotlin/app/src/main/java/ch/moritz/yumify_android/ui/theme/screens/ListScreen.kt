package ch.moritz.yumify_android.ui.theme.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.TextField
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.moritz.yumify_android.data.Meal
import ch.moritz.yumify_android.data.fetchMeals
import ch.moritz.yumify_android.data.fetchRandomMeal



import ch.moritz.yumify_android.UiState
import kotlinx.coroutines.launch

@Composable
fun ListScreen(onOpenDetail: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    var state by remember { mutableStateOf<UiState<List<Meal>>>(UiState.Idle) }
    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize().padding(16.dp)) {

        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.weight(1f),
                label = { Text("Search meals") },
                singleLine = true,
                keyboardActions = KeyboardActions(onSearch = { /* trigger search */ })
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                // minimal trigger
                state = UiState.Loading
                scope.launch {
                    state = runCatching { fetchMeals(query) }
                        .fold(
                            onSuccess = { UiState.Success(it) },
                            onFailure = { UiState.Error(it.message ?: "Error") }
                        )
                }
            }) { Text("Go") }

            Spacer(Modifier.width(32.dp))

            // Random button
            Button(onClick = {
                state = UiState.Loading
                scope.launch {
                    state = runCatching { fetchRandomMeal() }
                        .fold(
                            onSuccess = { UiState.Success(it) },
                            onFailure = { UiState.Error(it.message ?: "Error") }
                        )
                }
            }) { Text("Random") }

        }

        Spacer(Modifier.height(12.dp))

        when (val s = state) {
            UiState.Idle -> Text("Type and search.")
            UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text("Oops: ${s.message}")
            is UiState.Success -> {
                if (s.data.isEmpty()) Text("No results.")
                else LazyColumn {
                    items(s.data) { meal ->
                        ResultRow(
                            title = meal.title,
                            subtitle = meal.id,
                            // thumbnail optional; see note below
                            onClick = { onOpenDetail(meal.id) }
                        )
                        HorizontalDivider(
                            Modifier,
                            DividerDefaults.Thickness,
                            DividerDefaults.color
                        )
                    }
                }
            }
        }
    }
}

// Tiny row
@Composable
private fun ResultRow(title: String, subtitle: String?, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = { if (subtitle != null) Text(subtitle) },
        modifier = Modifier.clickable(onClick = onClick)
    )
}