package ua.blackwind.ui.compose

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import android.graphics.Bitmap
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
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

@Preview(widthDp = 310, heightDp = 600, uiMode = UI_MODE_NIGHT_NO)
@Composable
private fun CatFactCardPreview() {
    CatFactsTheme(darkTheme = false) {
        CatFactCard(
            catFact = CatFact(
                0,
                "Cats have the largest eyes of any mammal.",
                ""
            )
        )
    }
}