package com.samriddha.composepokedexapplication.data.remote.response.pokemonDetails

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)