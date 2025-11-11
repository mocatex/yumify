package ch.moritz.yumify_android.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter
import ch.moritz.yumify_android.data.fetchMealDetails
import ch.moritz.yumify_android.data.getIngredients
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    itemId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var meal by remember { mutableStateOf<ch.moritz.yumify_android.data.MealDto?>(null) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(itemId) {
        scope.launch {
            runCatching { fetchMealDetails(itemId) }
                .onSuccess {
                    meal = it
                    loading = false
                }
                .onFailure { loading = false }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(meal?.title ?: "Meal Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (loading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        } else meal?.let { m ->
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                item {
                    m.thumb?.let {
                        Image(
                            painter = rememberAsyncImagePainter(it),
                            contentDescription = m.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(bottom = 16.dp)
                        )
                    }

                    Text(
                        text = m.title ?: "",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${m.category ?: ""} • ${m.area ?: ""}",
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    Divider(Modifier.padding(vertical = 8.dp))

                    Text(
                        "Ingredients",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                items(m.getIngredients()) { (ingredient, measure) ->
                    Text("• $ingredient (${measure.ifEmpty { "n/a" }})")
                }

                item {
                    Divider(Modifier.padding(vertical = 12.dp))
                    Text("Instructions", style = MaterialTheme.typography.titleMedium)
                    Text(m.instructions.orEmpty(), Modifier.padding(top = 8.dp))
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text("No data found")
        }
    }
}
