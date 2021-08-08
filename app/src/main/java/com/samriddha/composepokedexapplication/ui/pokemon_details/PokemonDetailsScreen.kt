package com.samriddha.composepokedexapplication.ui.pokemon_details

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.samriddha.composepokedexapplication.data.remote.response.pokemonDetailsModels.PokemonData
import com.samriddha.composepokedexapplication.utils.Resource

@Composable
fun PokemonDetailsScreen(
    dominantColor:Color,
    pokemonName:String,
    navController: NavController,
    topPadding:Dp=20.dp,
    pokemonImageSize:Dp=200.dp,
    viewModel: PokemonDetailViewModel= hiltNavGraphViewModel()
) {

    val pokemonInfo= produceState<Resource<PokemonData>>(initialValue = Resource.Loading() ){
        value = viewModel.getPokemonInfo(pokemonName)
    }
}