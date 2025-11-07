package ch.moritz.yumify_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.moritz.yumify_android.ui.theme.Yumify_androidTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import ch.moritz.yumify_android.ui.theme.screens.DetailScreen
import ch.moritz.yumify_android.ui.theme.screens.ListScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Yumify_androidTheme {
                YumifyApp()
            }
        }
    }
}

@Composable
private fun YumifyApp() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        AppNavHost(modifier = Modifier.padding(innerPadding))
    }
}


object Routes {
    const val LIST = "list"
    const val DETAIL = "detail/{itemId}"
    fun detail(itemId: String) = "detail/$itemId"
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    // TODO inject your Repository or a ViewModel factory as needed
) {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Routes.LIST, modifier = modifier) {

        composable(Routes.LIST) {
            // TODO provide ViewModel via hiltViewModel() or factory
            ListScreen(
                onItemClick = { id -> nav.navigate(Routes.detail(id)) }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("itemId") ?: return@composable
            DetailScreen(
                itemId = id,
                onBack = { nav.popBackStack() }
            )
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Yumify_androidTheme {
        Greeting("Android")
    }
}