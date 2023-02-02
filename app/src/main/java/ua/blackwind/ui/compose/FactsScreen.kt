package ua.blackwind.ui.compose

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.blackwind.ui.model.CatFact
import ua.blackwind.ui.theme.CatFactsTheme

@Composable
fun CatFactsScreen(catFact: CatFact) {
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
            CatFactCard(catFact = catFact)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CatFactsScreenPreview() {
    CatFactsTheme(darkTheme = false) {
        CatFactsScreen(catFact = CatFact(0, "Cats have the largest eyes of any mammal.", ""))
    }
}