package ua.blackwind.ui.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        bottomBar = {

        },
        modifier = Modifier
    ) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            DestinationsNavHost(navGraph = NavGraphs.root)
        }

    }
}