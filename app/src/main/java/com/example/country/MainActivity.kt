package com.example.country

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.country.data.Country
import com.example.country.ui.theme.CountryTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CountryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CountryList(
                        countries = getSampleCountries(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CountryItem(country: Country, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = country.flagResId),
            contentDescription = "Drapeau de ${country.name}",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
        ) {
            Text(
                text = country.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Capitale: ${country.capital}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Code: ${country.code}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}


@Composable
fun CountryList(countries: List<Country>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(countries) { country ->
            CountryItem(country = country)
        }
    }
}


fun getSampleCountries(): List<Country> {
    return listOf(
        Country("France", "Paris", "FR", R.drawable.france),
        Country("Canada", "Ottawa", "CA", R.drawable.canada),
        Country("Japon", "Tokyo", "JP", R.drawable.japon),
        Country("Belgique", "Bruxelles", "BE", R.drawable.belgique),
        Country("Suisse", "Berne", "CH", R.drawable.suise),
        Country("Maroc", "Rabat", "MA", R.drawable.maroc),
        Country("Sénégal", "Dakar", "SN", R.drawable.senegale),
        Country("Brésil", "Brasília", "BR", R.drawable.bresil),
        Country("Allemagne", "Berlin", "DE", R.drawable.allmagne),
        Country("Italie", "Rome", "IT", R.drawable.itali)
    )
}

// 3. Prévisualisation : PreviewCountryList
@Preview(showBackground = true)
@Composable
fun PreviewCountryList() {
    CountryTheme {
        CountryList(countries = getSampleCountries())
    }
}
