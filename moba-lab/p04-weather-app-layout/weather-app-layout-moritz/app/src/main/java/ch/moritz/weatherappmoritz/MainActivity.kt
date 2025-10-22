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
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.app.Application
import kotlinx.coroutines.launch
import java.net.URL
import androidx.lifecycle.viewModelScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.beust.klaxon.Klaxon
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


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


// classes for parsing JSON response
class CurrentWeatherData(
    val temperature: Double, val weathercode: Int
)

class WeatherForecastData(
    val time: List<String>,
    val weathercode: List<Int>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>
)

class WeatherAPIResponse(
    val current_weather: CurrentWeatherData, val daily: WeatherForecastData
)

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
            color = Color.Transparent, contentColor = Color.White
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


class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val _weather = MutableLiveData<String>() // private mutable LiveData
    val weather: LiveData<String> = _weather // public immutable LiveData

    fun loadWeatherData() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    URL("https://api.open-meteo.com/v1/forecast?latitude=47.37&longitude=8.55&daily=weathercode,temperature_2m_max,temperature_2m_min&current_weather=true&timezone=Europe%2FBerlin").readText()
                }
                _weather.value = response

            } catch (e: Exception) {
                // Better error text for debugging:
                _weather.value = "Error: ${e::class.simpleName}: ${e.message ?: "(no message)"}"
                e.printStackTrace() // shows full stack in Logcat
            }
        }
    }
}


@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    unit: String = "no data found",
    primaryFontSize: TextUnit = 96.sp,
    secondaryFontSize: TextUnit = 36.sp,
    thirdFontSize: TextUnit = 16.sp,
    forecastData: List<List<String>> = listOf(),
    viewModel: WeatherViewModel = viewModel()
) {

    val weather by viewModel.weather.observeAsState("")

    LaunchedEffect(Unit) {
        viewModel.loadWeatherData()
    }

    val parsedWeather = remember(weather) {
        runCatching {
            if (weather.isNotBlank() && weather.trimStart()
                    .startsWith("{")
            ) Klaxon().parse<WeatherAPIResponse>(weather)
            else null
        }.getOrNull()
    }

    val temperature = parsedWeather?.current_weather?.temperature?.toString() ?: ""
    val weatherCondition = weatherCodeTitle(parsedWeather?.current_weather?.weathercode)
    val cityName = "Zurich" // hardcoded for now

    val forecastData = parsedWeather?.daily?.let {
        List(it.time.size) { index ->
            listOf(
                it.time[index],
                weatherCodeTitle(it.weathercode[index]),
                it.temperature_2m_max[index].toString(),
                it.temperature_2m_min[index].toString()
            )
        }
    }


    val textAlignment = Alignment.CenterHorizontally

    Column(
        modifier = modifier.padding(top = 150.dp)
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
    forecastData: List<List<String>>?,
    fontSize: TextUnit = 16.sp
) {
    Column() {
        if (forecastData != null) {
            for (dayForecast in forecastData) {
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

}

fun getDayName(dateString: String): String = try {
    val date = LocalDate.parse(dateString) // ISO yyyy-MM-dd by default
    date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) // Mon, Tue, ...
} catch (e: Exception) {
    "?"
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

    Row() {
        Text(
            text = getDayName(day),
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .weight(1f)
                .padding(start = 32.dp)
        )
        Text(
            text = weatherCondition, fontSize = fontSize, modifier = Modifier.weight(2f)
        )
        Text(
            text = "$maxTempSymbol $maxTemperature",
            fontSize = fontSize,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
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

fun weatherCodeTitle(weatherCode: Int?): String {
    return when (weatherCode) {
        0 -> "Clear sky"
        1 -> "Mainly clear"
        2, 3 -> "Partly Cloudy"
        in 40..49 -> "Fog or Ice Fog"
        in 50..59 -> "Drizzle"
        in 60..69 -> "Rain"
        in 70..79 -> "Snow Fall"
        in 80..84 -> "Rain Showers"
        85, 86 -> "Snow Showers"
        87, 88 -> "Shower(s) of Snow Pellets"
        89, 90 -> "Hail"
        in 91..99 -> "Thunderstorm"
        else -> "loading..."
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherAppPreview() {
    WeatherAppMoritzTheme {
        WeatherApp()
    }
}