package ua.blackwind

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.blackwind.data.CatFactsRepository
import ua.blackwind.data.ICatFactsRepository
import ua.blackwind.data.db.model.RandomCatFactDbModel
import javax.inject.Inject

@HiltViewModel
class RandomFactsViewModel @Inject constructor(private val repository: CatFactsRepository):
    ViewModel() {
    private val _list = MutableStateFlow(emptyList<RandomCatFactDbModel>())
    val list = _list as StateFlow<List<RandomCatFactDbModel>>

    init {
        repository.fetchNewRandomCatFacts()

        viewModelScope.launch {
            repository.getAllRandomCatFacts().collectLatest { _list.update { it } }
        }
    }

}