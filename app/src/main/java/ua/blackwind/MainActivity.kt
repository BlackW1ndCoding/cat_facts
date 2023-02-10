package ua.blackwind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ua.blackwind.ui.compose.BottomBarIconResources
import ua.blackwind.ui.compose.MainScreen
import ua.blackwind.ui.theme.CatFactsTheme

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CatFactsTheme {
                MainScreen(
                    BottomBarIconResources(
                        R.drawable.cat_face_filled,
                        R.drawable.paw_filled
                    )
                )
            }
        }
    }
}