package ch.moritz.yumify_android.ui.theme.screens

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text


@Composable
fun DetailScreen(
    itemId: String,
    onBack: () -> Unit
) {
    Text("Detail Screen for item: $itemId")
    // TODO: fetch or read cached item by id
    // TODO: show top app bar with Back action calling onBack()
}