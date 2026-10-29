package gourmet.cwaorecasting.hubapp.ui.composable.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import gourmet.cwaorecasting.hubapp.ui.composable.screen.about.GTENPAboutScreen
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.GTENPGameScreen
import gourmet.cwaorecasting.hubapp.ui.composable.screen.high_scores.GTENPHighScoresScreen
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.GTENPMainMenuScreen
import gourmet.cwaorecasting.hubapp.ui.composable.screen.splash.GTENPSplashScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val activity = LocalActivity.current

    NavHost(
        navController = navController,
        startDestination = NavRoute.Splash,
        modifier = modifier,
    ) {
        composable<NavRoute.Splash> {
            GTENPSplashScreen(
                onMainMenuScreenNavigation = {
                    navController.navigate(route = NavRoute.MainMenu) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<NavRoute.MainMenu>(
            deepLinks = listOf(navDeepLink { uriPattern = "cwaorecasting://app/home" })
        ) {
            GTENPMainMenuScreen(
                onPlayClick = { difficulty ->
                    navController.navigate(
                        route = NavRoute.Game(
                            difficulty
                        )
                    )
                },
                onHighScoresScreenNavigation = { navController.navigate(route = NavRoute.HighScores) },
                onAboutScreenNavigation = { navController.navigate(route = NavRoute.About) },
                onExit = { activity?.finishAffinity() }
            )
        }

        composable<NavRoute.Game>(
            deepLinks = listOf(
                navDeepLink { uriPattern = "cwaorecasting://app/game?difficulty={difficulty}" },
                navDeepLink { uriPattern = "cwaorecasting://app/game" },
            )
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<NavRoute.Game>()
            GTENPGameScreen(
                difficulty = route.difficulty,
                onMainMenu = {
                    navController.navigate(route = NavRoute.MainMenu) {
                        popUpTo(NavRoute.MainMenu) {
                            inclusive = true
                        }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<NavRoute.About>(
            deepLinks = listOf(navDeepLink { uriPattern = "cwaorecasting://app/about" })
        ) {
            GTENPAboutScreen(onBackClick = { navController.popBackStack() })
        }

        composable<NavRoute.HighScores>(
            deepLinks = listOf(navDeepLink { uriPattern = "cwaorecasting://app/highscores" })
        ) {
            GTENPHighScoresScreen(onBackClick = { navController.popBackStack() })
        }
    }
}
