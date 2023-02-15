package ua.blackwind.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.alexstyl.swipeablecard.*
import ua.blackwind.ui.model.CatFact
import ua.blackwind.ui.theme.CatFactsTheme

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun CatFactCard(
    catFact: CatFact,
    state: SwipeableCardState,
    onSwipe: (CatFact) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .swipableCard(
                state = state,
                onSwiped = { onSwipe(catFact) }
            )
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()

        ) {
            val boxWidth = this.maxWidth
            val boxHeight = this.maxHeight

            Surface(
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .align(alignment = Alignment.TopCenter)
                    .width(boxWidth)
                    .height(boxHeight * .7f)
            ) {
                AsyncImage(
                    model = catFact.imgUrl, contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
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
                    .heightIn(boxHeight * .4f, boxHeight * .6f)
            ) {
                Column() {
                    Text(
                        text = catFact.text,
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .sizeIn(
                                minHeight = boxHeight * .4f - 55.dp,
                                maxHeight = boxHeight * .6f - 55.dp
                            )
                            .padding(horizontal = 20.dp, vertical = 30.dp)
                            .verticalScroll(rememberScrollState())
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                    )
                }
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
                text = "Looks like we are out of cat facts to show.\n >_<\n Just press paw to load more. ^_^",
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
            ),
            rememberSwipeableCardState(),
            {}
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