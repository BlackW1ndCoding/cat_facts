package ua.blackwind.ui.screens.random_facts

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alexstyl.swipeablecard.SwipeableCardState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ua.blackwind.R
import ua.blackwind.ui.compose.CatFactCard
import ua.blackwind.ui.compose.CatFactCardNoMoreFacts
import ua.blackwind.ui.model.CatFact
import ua.blackwind.ui.theme.CatFactsTheme

@Destination(start = true)
@Composable
fun CatFactsScreen(
    navigator: DestinationsNavigator
) {
    val viewModel = hiltViewModel<RandomCatFactsViewModel>()
    val facts by viewModel.facts.collectAsState(initial = emptyList())
    val states = facts.reversed().map { it to rememberSwipeableCardState(facts) }
    CatFactsScreenUi(states, viewModel::onSwipe, viewModel::onFavoriteClick)
}

@Composable
fun CatFactsScreenUi(
    states: List<Pair<CatFact, SwipeableCardState>>,
    onCardSwipe: (CatFact) -> Unit,
    onFavoriteButtonPress: () -> Unit
) {

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
            Box {

                CatFactCardNoMoreFacts()
                states.forEach { (fact, state) ->
                    if (state.swipedDirection == null) {
                        CatFactCard(catFact = fact, state = state, onSwipe = onCardSwipe)
                    }
                }
                Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                    IconButton(
                        onClick = { onFavoriteButtonPress() },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.secondary
                        ),
                        modifier = Modifier
                            .size(60.dp)


                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.paw_not_filled),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        }
    }
}

@Composable
private fun rememberSwipeableCardState(key: Any): SwipeableCardState {
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }
    val screenHeight = with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.dp.toPx()
    }
    return remember(key) {
        SwipeableCardState(screenWidth, screenHeight)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CatFactsScreenPreview() {
    CatFactsTheme(darkTheme = false) {
        CatFactsScreenUi(
            listOf(CatFact(0, "Hello, Kitty!", "") to rememberSwipeableCardState(0)),
            {}, {}
        )
    }
}