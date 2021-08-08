package com.samriddha.composepokedexapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.samriddha.composepokedexapplication.ui.pokemon_list.PokemonListScreen
import com.samriddha.composepokedexapplication.ui.theme.ComposePokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePokedexTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "pokemon_list_screen"){
                    composable("pokemon_list_screen"){
                        ///////// Pokemon List Screen ///////////
                        PokemonListScreen(navController = navController)
                    }
                    composable("pokemon_details_screen/{POKEMON_COLOR}/{POKEMON_NAME}",
                        arguments = listOf(
                            navArgument("POKEMON_COLOR"){
                                type = NavType.IntType
                            },
                            navArgument("POKEMON_NAME"){
                                type = NavType.StringType
                            }
                        )
                    ){
                        ///////// Pokemon Details Screen ///////////
                        val pokemonColor = remember{
                            val color = it.arguments?.getInt("POKEMON_COLOR")
                            color?.let { Color(it) ?: Color.White }
                        }

                        val pokemonName = remember{
                            it.arguments?.getString("POKEMON_NAME")
                        }
                    }
                }
            }
        }
    }
}
