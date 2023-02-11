package ua.blackwind.ui.screens.favorite_facts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import ua.blackwind.ui.model.CatFact

@Destination()
@Composable
fun FavoriteFactsListScreen(navigator: DestinationsNavigator) {
    val viewModel = hiltViewModel<FavoriteCatFactsViewModel>()
    val facts = viewModel.facts.collectAsState()
    FavoriteFactsListScreenUi(facts.value)
}

@Composable
fun FavoriteFactsListScreenUi(list: List<CatFact>) {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Your favorite cat facts:",
            fontSize = 24.sp,
            fontStyle = FontStyle.Italic
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )
        Surface(color = MaterialTheme.colorScheme.primary) {
            LazyColumn {
                items(list.size) { index ->
                    FavoriteFactListItem(catFact = list[index])
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FavoriteFactsListScreenPreview() {
    FavoriteFactsListScreen(
        EmptyDestinationsNavigator
    )
}