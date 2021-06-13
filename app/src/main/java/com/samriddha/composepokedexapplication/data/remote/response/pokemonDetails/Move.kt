package com.samriddha.composepokedexapplication.data.remote.response.pokemonDetails

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)