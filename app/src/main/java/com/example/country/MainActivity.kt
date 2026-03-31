package com.example.country

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.country.data.Country
import com.example.country.ui.theme.CountryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CountryTheme {
                var showList by remember { mutableStateOf(false) }

                if (showList) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            CountryTopAppBar()
                        }
                    ) { innerPadding ->
                        CountryList(
                            countries = getSampleCountries(),
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                } else {
                    WelcomeScreen(onStartClicked = { showList = true })
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(onStartClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenue",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 64.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onStartClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "voir les pays",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Public,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "My Countrys",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
        ),
        modifier = modifier
    )
}

@Composable
fun CountryItem(country: Country, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .clickable { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = country.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${country.capital}/${country.code}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (expanded) "Voir moins" else "Voir plus",
                        modifier = Modifier.size(32.dp),
                        tint = Color.Black
                    )
                }
            }

            if (expanded) {
                Column(
                    modifier = Modifier.padding(
                        top = 16.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    )
                ) {
                    Text(
                        text = "Détails :",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = country.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
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
        Country(
            "France", "Paris", "FR", R.drawable.france,
            "La France est célèbre pour sa gastronomie, sa culture et la tour Eiffel."
        ),
        Country(
            "Canada", "Ottawa", "CA", R.drawable.canada,
            "Le deuxième plus grand pays du monde, connu pour ses paysages naturels et le hockey."
        ),
        Country(
            "Japon", "Tokyo", "JP", R.drawable.japon,
            "Archipel volcanique réputé pour sa technologie de pointe et ses traditions ancestrales."
        ),
        Country(
            "Belgique", "Bruxelles", "BE", R.drawable.belgique,
            "Pays connu pour son chocolat, ses gaufres et être le siège de l'Union Européenne."
        ),
        Country(
            "Suisse", "Berne", "CH", R.drawable.suise,
            "Célèbre pour ses Alpes, ses montres de luxe et son chocolat."
        ),
        Country(
            "Maroc", "Rabat", "MA", R.drawable.maroc,
            "Royaume d'Afrique du Nord connu pour ses médinas, son désert et son thé à la menthe."
        ),
        Country(
            "Sénégal", "Dakar", "SN", R.drawable.senegale,
            "Pays de la 'Teranga' (hospitalité), connu pour l'île de Gorée et sa musique."
        ),
        Country(
            "Brésil", "Brasília", "BR", R.drawable.bresil,
            "Le plus grand pays d'Amérique latine, célèbre pour son carnaval et le football."
        ),
        Country(
            "Allemagne", "Berlin", "DE", R.drawable.allmagne,
            "Puissance économique majeure connue pour son histoire, son ingénierie et l'Oktoberfest."
        ),
        Country(
            "Italie", "Rome", "IT", R.drawable.itali,
            "Berceau de l'Empire romain, réputé pour son art, sa mode et sa cuisine (pizza, pâtes)."
        )
    )
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    CountryTheme {
        WelcomeScreen(onStartClicked = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    CountryTheme {
        Scaffold(
            topBar = { CountryTopAppBar() }
        ) { innerPadding ->
            CountryList(
                countries = getSampleCountries(),
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
