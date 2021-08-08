package com.samriddha.composepokedexapplication.ui.pokemon_details

import androidx.lifecycle.ViewModel
import com.samriddha.composepokedexapplication.data.remote.response.pokemonDetailsModels.PokemonData
import com.samriddha.composepokedexapplication.data.repository.PokemonRepository
import com.samriddha.composepokedexapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel
@Inject
constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    suspend fun getPokemonInfo(pokemonName:String):Resource<PokemonData> {
        return repository.getPokemonByName(pokemonName)
    }
}