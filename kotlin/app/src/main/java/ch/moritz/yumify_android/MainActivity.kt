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

@Preview(showBackground = true)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier
    // TODO inject your Repository or a ViewModel factory as needed
) {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "list") {

        composable("list") {
            ListScreen(
                onOpenDetail = { id -> nav.navigate("detail/$id") }
            )
        }

        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            // for you Julien :)
            val itemId = it.arguments?.getString("id") ?: ""
            DetailScreen(
                itemId = itemId,
                onBack = { nav.popBackStack() }
            )
        }
    }

}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    Yumify_androidTheme {
//        Greeting("Android")
//    }
//}