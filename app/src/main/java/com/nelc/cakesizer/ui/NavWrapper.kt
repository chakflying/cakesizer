package com.nelc.cakesizer.ui

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nelc.cakesizer.welcomeactivity.WelcomeScreen
import kotlinx.coroutines.flow.MutableSharedFlow

data class NavEvents(
    val startArEvents: MutableSharedFlow<String>
)

@Composable
fun NavWrapper(
    exit: () -> Unit,
    navEvents: NavEvents,
) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val updateRoute = { newRoute: String ->
        navController.navigate(newRoute) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            // popUpTo(navController.graph.findStartDestination().id) {
            //     saveState = true
            // }
            // Avoid multiple copies of the same destination when
            // re-selecting the same item
            launchSingleTop = true
            // Restore state when re-selecting a previously selected item
            restoreState = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = "welcome",
        modifier = Modifier.systemBarsPadding(),
    ) {
        composable("welcome") {
            WelcomeScreen(updateRoute, navEvents)
        }
    }

}