package ch.moritz.yumify_android.ui.theme.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun ListScreen(
    onItemClick: (String) -> Unit
) {
    Text("List Screen")
    // TODO: show search field + button
    // TODO: show list of results; call onItemClick(result.id) when tapped
}