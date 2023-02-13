package ua.blackwind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ua.blackwind.ui.screens.main.BottomBarIconResources
import ua.blackwind.ui.screens.main.MainScreen
import ua.blackwind.ui.theme.CatFactsTheme

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CatFactsTheme {
                MainScreen(
                    bottomBarIcons = BottomBarIconResources(
                        R.drawable.cat_face_filled,
                        R.drawable.paw_filled
                    )
                )
            }
        }
    }
}