package com.samriddha.composepokedexapplication.data.remote.response.pokemonDetailsModels

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)