package gourmet.cwaorecasting.hubapp.ui.composable.navigation

import gourmet.cwaorecasting.hubapp.data.model.Difficulty
import kotlinx.serialization.Serializable

sealed class NavRoute {
    @Serializable
    object Splash : NavRoute()

    @Serializable
    object MainMenu : NavRoute()

    @Serializable
    object About : NavRoute()

    @Serializable
    object HighScores : NavRoute()

    @Serializable
    data class Game(val difficulty: Difficulty) : NavRoute()
}