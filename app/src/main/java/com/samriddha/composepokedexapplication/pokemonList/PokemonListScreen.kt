package com.samriddha.composepokedexapplication.pokemonList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samriddha.composepokedexapplication.R

@Composable
fun PokemonListScreen(navController: NavController){

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            //Initial space above pokemon logo.
            Spacer(modifier = Modifier.height(20.dp))

            //Pokemon logo image
            Image(painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = "Pokemon logo",
            modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally))

            //Search bar
            SearchBar(
                hint = "Search Pokemon...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp)
            ){

            }


        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint:String="",
    onSearch: (String)->Unit = {}
){
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember{
        mutableStateOf(hint!="")
    }

    Box(modifier = modifier) {

        /*This field will take the user input: Edit text*/
        BasicTextField(value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(6.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 18.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it != FocusState.Active
                }
            )

        /*For showing the hint*/
        if (isHintDisplayed) {
            Text(text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp)
                )
        }
    }
}