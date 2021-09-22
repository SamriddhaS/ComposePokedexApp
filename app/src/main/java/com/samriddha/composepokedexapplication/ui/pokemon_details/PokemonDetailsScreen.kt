package com.samriddha.composepokedexapplication.ui.pokemon_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.bitmap.BitmapPool
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.Transformation
import com.google.accompanist.coil.rememberCoilPainter
import com.samriddha.composepokedexapplication.R
import com.samriddha.composepokedexapplication.data.remote.response.pokemonDetailsModels.PokemonData
import com.samriddha.composepokedexapplication.utils.Resource

@Composable
fun PokemonDetailsScreen(
    dominantColor: Color,
    pokemonName:String,
    navController: NavController,
    topPadding:Dp=20.dp,
    pokemonImageSize:Dp=200.dp,
    viewModel: PokemonDetailViewModel= hiltViewModel()
) {

    val pokemonInfo= produceState<Resource<PokemonData>>(initialValue = Resource.Loading() ){
        value = viewModel.getPokemonInfo(pokemonName)
    }.value

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = dominantColor)
        .padding(bottom = 18.dp)
    ) {


        PokemonDetailsTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .fillMaxWidth()
                .align(Alignment.TopCenter)
            )

        /*
        * White box section
        * */
        PokemonDetailsStateWrapper(
            pokemonInfo = pokemonInfo,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topPadding+pokemonImageSize/2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 18.dp
                )
                .shadow(10.dp, RoundedCornerShape(18.dp))
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(top = topPadding+pokemonImageSize/2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 18.dp
                )
            )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
            ) {
            if (pokemonInfo is Resource.Success){
                pokemonInfo.data?.sprites?.let {

                    val painter = rememberCoilPainter(request = ImageRequest.Builder(context = LocalContext.current)
                        .data(it.front_default)
                        .placeholder(R.drawable.ic_international_pok_mon_logo)
                        .build()
                    )

                    Image(painter = painter,
                        contentDescription = pokemonInfo.data.name,
                        modifier = Modifier
                            .size(pokemonImageSize)
                            .offset(y = topPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonDetailsTopSection(
    navController: NavController,
    modifier: Modifier = Modifier

) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .background(
                Brush.Companion.verticalGradient(
                colors = listOf(
                    Color.Black,
                    Color.Transparent
                ),
                    startY = 0f,
                    endY = 500f,
                    tileMode = TileMode.Repeated
            ))
    ) {

        Icon(imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(18.dp, 18.dp)
                .clickable {
                    navController.popBackStack()
                }
            )
    }
}

@Composable
fun PokemonDetailsStateWrapper(
    pokemonInfo:Resource<PokemonData>,
    modifier:Modifier=Modifier,
    loadingModifier:Modifier=Modifier
) {
    when(pokemonInfo){
        is Resource.Success -> {

        }
        is Resource.Error -> {
            Text(text = pokemonInfo.message!!,
                color = Color.Red,
                modifier = modifier
                )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
    }
}