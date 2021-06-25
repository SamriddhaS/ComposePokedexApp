package com.samriddha.composepokedexapplication.data.remote.response.pokemonDetailsModels

data class VersionGroupDetail(
    val level_learned_at: Int,
    val move_learn_method: MoveLearnMethod,
    val version_group: VersionGroup
)