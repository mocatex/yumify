package ch.moritz.weatherappmoritz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ch.moritz.weatherappmoritz.ui.theme.WeatherAppMoritzTheme
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppMoritzTheme {
                WeatherApp()
            }
        }
    }
}

@Composable
fun WeatherApp() {

    // hardcoded forecast data
    val forecastData = listOf(
        listOf("Mon", "partly cloudy", "20.1", "18.1"),
        listOf("Tue", "sunny", "22.3", "19.4"),
        listOf("Wed", "rainy", "19.0", "17.5"),
        listOf("Thu", "cloudy", "21.5", "18.9"),
        listOf("Fri", "sunny", "23.0", "20.0")
    )

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Surface(
            color = Color.Transparent,
            contentColor = Color.White
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Background Image for the corresponding city
                Image(
                    painter = painterResource(R.drawable.background_zurich),
                    contentDescription = "Background Image of City",
                    contentScale = ContentScale.Crop
                )

                WeatherScreen(
                    modifier = Modifier.fillMaxSize(),
                    weatherCondition = "partly cloudy",
                    cityName = "Zurich",
                    temperature = "21.1",
                    unit = "°C",
                    primaryFontSize = 96.sp,
                    secondaryFontSize = 30.sp,
                    thirdFontSize = 16.sp,
                    forecastData = forecastData
                )
            }
        }

    }
}

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    weatherCondition: String = "no data found",
    cityName: String = "no data found",
    temperature: String = "no data found",
    unit: String = "no data found",
    primaryFontSize: TextUnit = 96.sp,
    secondaryFontSize: TextUnit = 36.sp,
    thirdFontSize: TextUnit = 16.sp,
    forecastData: List<List<String>> = listOf()
) {

    val textAlignment = Alignment.CenterHorizontally

    Column(
        modifier = modifier
            .padding(top = 150.dp)
    ) {

        Text(
            text = cityName,
            fontSize = secondaryFontSize,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.align(textAlignment)
        )
        Text(
            text = weatherCondition,
            fontSize = secondaryFontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(textAlignment)
        )
        Text(
            text = "$temperature$unit",
            fontSize = primaryFontSize,
            modifier = Modifier.align(textAlignment)
        )

        WeatherForecast(forecastData, thirdFontSize)
    }
}

@Composable
fun WeatherForecast(
    forecastData: List<List<String>>,
    fontSize: TextUnit = 16.sp
)
{
    Column(){
        for (dayForecast in forecastData){
            WeatherForecastItem(
                day = dayForecast[0],
                weatherCondition = dayForecast[1],
                maxTemperature = dayForecast[2],
                minTemperature = dayForecast[3],
                fontSize = fontSize
            )
        }
    }

}
@Composable
fun WeatherForecastItem(
    day: String,
    weatherCondition: String,
    maxTemperature: String,
    minTemperature: String,
    fontSize: TextUnit = 16.sp
) {
    val minTempSymbol = "\u2191"
    val maxTempSymbol = "\u2193"
    Row(){
        Text(
            text = day,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .weight(1f)
                .padding(start = 32.dp)
        )
        Text(
            text = weatherCondition,
            fontSize = fontSize,
            modifier = Modifier
                .weight(2f)
        )
        Text(
            text = "$maxTempSymbol $maxTemperature",
            fontSize = fontSize,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = "$minTempSymbol $minTemperature",
            fontSize = fontSize,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.End,
            modifier = Modifier
                .weight(1f)
                .padding(end = 32.dp)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun WeatherAppPreview() {
    WeatherAppMoritzTheme {
        WeatherApp()
    }
}