package ua.blackwind.ui.screens.favorite_facts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.blackwind.ui.model.CatFact


@Composable
fun FavoriteFactListItem(catFact: CatFact) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(40.dp, Dp.Unspecified)
    )
    {
        Text(
            text = "- ${catFact.text}",
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}

@Preview
@Composable
private fun FavoriteFactListItemPreview() {
    FavoriteFactListItem(catFact = CatFact(0, "Cats have the largest eyes of any mammal.", ""))
}