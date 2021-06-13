package com.samriddha.composepokedexapplication.data.remote.response.pokemonDetails

data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)