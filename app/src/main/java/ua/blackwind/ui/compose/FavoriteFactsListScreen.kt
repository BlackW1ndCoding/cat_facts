package ua.blackwind.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavOptionsBuilder
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ua.blackwind.ui.model.CatFact

@Composable
fun FavoriteFactsListScreen(list: List<CatFact>, navigator: DestinationsNavigator) {
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
        Surface() {
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
        list = listOf(
            CatFact(
                0,
                "Cats have the largest eyes of any mammal.",
                ""
            ),
            CatFact(
                0,
                "When cats run, their backs contract and extend to give them maximum stride. Their shoulder blades are not attached with bone, but with muscle, and this gives a cat even greater extension and speed.",
                ""
            ),
            CatFact(
                0,
                "Cats and kittens should be acquired in pairs whenever possible as cat families interact best in pairs.",
                ""
            )
        ),
        dummyNavigator
    )
}