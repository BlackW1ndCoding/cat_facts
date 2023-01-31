package ua.blackwind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ua.blackwind.data.db.model.RandomCatFactDbModel
import ua.blackwind.ui.theme.CatFactsTheme

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    private val viewModel by viewModels<RandomFactsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val list = viewModel.list.collectAsState()
            CatFactsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    catList(list = list.value)
                }
            }
        }
    }
}

@Composable
fun catList(list: List<RandomCatFactDbModel>) {
    LazyColumn {
        items(list.size) { catFactItem(catFactDbModel = list[it]) }
    }
}

@Composable
fun catFactItem(catFactDbModel: RandomCatFactDbModel) {
    Text(text = catFactDbModel.text)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CatFactsTheme {
    }
}