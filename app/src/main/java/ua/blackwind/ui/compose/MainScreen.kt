package ua.blackwind.ui.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.spec.NavGraphSpec
import ua.blackwind.R
import ua.blackwind.ui.theme.CatFactsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    bottomBarIcons: BottomBarIconResources,
    //navigator: NavGr
) {
    Scaffold(
        bottomBar = {
            Box() {

                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                ) {
                    //TODO create icon shadow shapes from svg
                    this.NavigationBarItem(
                        selected = true, onClick = {  },
                        icon = {
                            Icon(
                                painter = painterResource(id = bottomBarIcons.randomIcon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp, 45.dp)
                                    .shadow(2.dp)
                            )
                        },
                        enabled = true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.secondary,
                            unselectedIconColor = MaterialTheme.colorScheme.secondary,
                            indicatorColor = MaterialTheme.colorScheme.tertiary
                        )
                    )
                    this.NavigationBarItem(selected = false, onClick = { /*TODO*/ },
                        enabled = false,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.secondary,
                            unselectedIconColor = MaterialTheme.colorScheme.secondary,
                            indicatorColor = MaterialTheme.colorScheme.tertiary
                        ),
                        icon = {
                            Icon(
                                painter = painterResource(id = bottomBarIcons.favoriteIcon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp, 40.dp)
                                    .shadow(2.dp)
                            )
                        }
                    )

                }

                Divider(
                    thickness = 1.dp, color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )
            }
        },
        modifier = Modifier
    ) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            DestinationsNavHost(navGraph = NavGraphs.root)
        }

    }
}

data class BottomBarIconResources(
    @DrawableRes val randomIcon: Int,
    @DrawableRes val favoriteIcon: Int
)

@Preview(showSystemUi = true)
@Composable
private fun MainScreenPreview() {
    CatFactsTheme {

        MainScreen(BottomBarIconResources(R.drawable.cat_face_filled, R.drawable.paw_filled))
    }
}