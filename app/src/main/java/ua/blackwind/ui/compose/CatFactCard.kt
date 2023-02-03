package ua.blackwind.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.blackwind.ui.model.CatFact
import ua.blackwind.ui.theme.CatFactsTheme

@Composable
fun CatFactCard(catFact: CatFact) {
    Card(shape = RoundedCornerShape(12.dp)) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()

        ) {
            val boxWidth = this.maxWidth
            val boxHeight = this.maxHeight
//            Image(
//                painter = BitmapPainter(image),
//                contentDescription = "cat",
//                Modifier.weight(0.6f)
//            )
            Surface(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(alignment = Alignment.TopCenter)
                    .width(boxWidth)
                    .height(boxHeight * 0.7f)
            ) {
                Text(text = "THIS IS SPARTA!!!!")
            }
            Surface(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp
                ),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .width(boxWidth)
                    .height(boxHeight * 0.4f)
            ) {
                Text(
                    text = catFact.text,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 30.dp)
                )
            }
        }
    }
}

@Composable
fun CatFactCardNoMoreFacts() {
    Card(shape = RoundedCornerShape(12.dp)) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Looks like we are out of cat facts to show.\n >_<\n Just press paw to load more cat facts. ^_^",
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(widthDp = 310, heightDp = 600)
@Composable
private fun CatFactCardPreview() {
    CatFactsTheme(darkTheme = true) {
        CatFactCard(
            catFact = CatFact(
                0,
                "Cats have the largest eyes of any mammal.",
                ""
            )
        )
    }
}

@Preview(widthDp = 310, heightDp = 600)
@Composable
private fun CatFactCardNoMoreFactsPreview() {
    CatFactsTheme(darkTheme = false) {
        CatFactCardNoMoreFacts()
    }
}