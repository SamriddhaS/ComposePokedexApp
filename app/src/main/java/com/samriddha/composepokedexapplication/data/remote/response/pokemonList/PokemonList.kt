package com.samriddha.composepokedexapplication.data.remote.response.pokemonList

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)