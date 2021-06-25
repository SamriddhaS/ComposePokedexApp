package com.samriddha.composepokedexapplication.data.remote.response.pokemonDetailsModels

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)