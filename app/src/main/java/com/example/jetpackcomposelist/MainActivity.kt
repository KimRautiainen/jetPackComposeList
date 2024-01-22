package com.example.jetpackcomposelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpackcomposelist.ui.theme.JetPackComposeListTheme

data class President(val name: String, val startDuty: Int, val endDuty: Int, val description: String)

object DataProvider {
    val presidents: kotlin.collections.MutableList<President> = java.util.ArrayList()

    init {
// construct the data source
        presidents.add(President("Kaarlo Stahlberg", 1919, 1925, "Eka presidentti"))
        presidents.add(President("Lauri Relander", 1925, 1931, "Toka presidentti"))
        presidents.add(President("P. E. Svinhufvud", 1931, 1937, "Kolmas presidentti"))
        presidents.add(President("Kyösti Kallio", 1937, 1940, "Neljas presidentti"))
        presidents.add(President("Risto Ryti", 1940, 1944, "Viides presidentti"))
        presidents.add(President("Carl Gustaf Emil Mannerheim", 1944, 1946, "Kuudes presidentti"))
        presidents.add(President("Juho Kusti Paasikivi", 1946, 1956, "Äkäinen ukko"))
        presidents.add(President("Urho Kekkonen", 1956, 1982, "Pelimies"))
        presidents.add(President("Mauno Koivisto", 1982, 1994, "Manu"))
        presidents.add(President("Martti Ahtisaari", 1994, 2000, "Mahtisaari"))
        presidents.add(President("Tarja Halonen", 2000, 2012, "Eka naispresidentti"))
        presidents.add(President("Sauli Niinistö", 2012, 2024, "Ensimmäisen koiran, Oskun, omistaja"))

    }
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackComposeListTheme {
                // NavController setup
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "presidentsList") {
                    composable("presidentsList") {
                        PresidentsListScreen(navController)
                    }
                    composable("presidentDetail/{presidentName}", arguments = listOf(navArgument("presidentName") { type = NavType.StringType })) { backStackEntry ->
                        val presidentName = backStackEntry.arguments?.getString("presidentName")
                        val president = DataProvider.presidents.find { it.name == presidentName }
                        president?.let { PresidentDetailScreen(it) }
                    }
                }
            }
        }
    }
}


@Composable
fun PresidentsListScreen(navController: NavController) {
    LazyColumn {
        items(DataProvider.presidents) { president ->
            Text(text = president.name, modifier = Modifier.clickable {
                navController.navigate("presidentDetail/${president.name}")
            })
            Divider()
        }
    }
}
@Composable
fun PresidentDetailScreen(president: President) {
    Column {
        Text("Name: ${president.name}")
        Text("Start Duty: ${president.startDuty}")
        Text("End Duty: ${president.endDuty}")
        Text("Description: ${president.description}")
    }
}
