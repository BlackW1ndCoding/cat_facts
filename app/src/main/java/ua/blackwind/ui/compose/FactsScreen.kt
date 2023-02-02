package ua.blackwind.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import ua.blackwind.ui.model.CatFact

@Composable
fun CatFactsScreen(catFact: CatFact) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column() {
            Text(text = "Your random cat facts:", fontSize = 24.sp)
        }
    }
}