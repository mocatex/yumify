package com.example.weatherapplayoutjulien

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.weatherapplayoutjulien.ui.theme.WeatherAppLayoutJulienTheme
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.width
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppLayoutJulienTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        Image(
                            painter = painterResource(id = R.drawable.zurich),
                            contentDescription = "Background",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            WeatherInfo(modifier = Modifier.align(BiasAlignment(0f, -0.6f)))
                            WeatherTable(modifier = Modifier
                                .align(BiasAlignment(0f, 0f))
                                .padding(horizontal = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherInfo(modifier: Modifier = Modifier) {
    var color = Color.White
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "partly cloudy",
            style = MaterialTheme.typography.labelMedium,
            color = color
        )
        Text(
            text = "Zurich",
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
        Text(
            text = "12.1°",
            style = MaterialTheme.typography.titleLarge,
            color = color
        )
    }
}

@Composable
fun WeatherTable(modifier: Modifier = Modifier) {
    val weatherData = listOf(
        WeatherDay("Fri", "sunny", 8, 18),
        WeatherDay("Sat", "cloudy", 6, 15),
        WeatherDay("Sun", "rainy", 4, 12),
        WeatherDay("Mon", "snowy", -2, 5),
        WeatherDay("Tue", "windy", 3, 14)
    )
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        weatherData.forEach { weather ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 1.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = weather.day,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(40.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = weather.condition,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.width(80.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "↓${weather.minTemp}°",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "↑${weather.maxTemp}°",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(40.dp)
                )
            }
        }
    }
}

data class WeatherDay(
    val day: String,
    val condition: String,
    val minTemp: Int,
    val maxTemp: Int
)