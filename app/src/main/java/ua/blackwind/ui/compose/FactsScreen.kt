package ua.blackwind.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ua.blackwind.ui.CatFactsViewModel
import ua.blackwind.ui.model.CatFact
import ua.blackwind.ui.theme.CatFactsTheme

@Destination(start = true)
@Composable
fun CatFactsScreen(navigator: DestinationsNavigator) {
    val viewModel = hiltViewModel<CatFactsViewModel>()
    val currentFact by viewModel.currentFact.collectAsState()

    CatFactsScreenUi(currentFact)
}

@Composable
fun CatFactsScreenUi(catFact: CatFact?) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(Modifier.padding(horizontal = 25.dp, vertical = 15.dp)) {
            Text(
                text = "Your random cat facts:",
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp)
            )
            if (catFact != null) {
                CatFactCard(catFact = catFact)
            } else {
                CatFactCardNoMoreFacts()
            }

        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun CatFactsScreenPreview() {
    CatFactsTheme(darkTheme = false) {
        CatFactsScreen(
            navigator = dummyNavigator
        )
    }
}