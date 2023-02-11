package ua.blackwind.ui.screens.favorite_facts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.blackwind.data.cat_facts.CatFactsRepository
import ua.blackwind.ui.model.CatFact
import ua.blackwind.ui.model.toCatFact
import javax.inject.Inject

@HiltViewModel
class FavoriteCatFactsViewModel @Inject constructor(
    factsRepository: CatFactsRepository
): ViewModel() {
    private val _facts = MutableStateFlow<List<CatFact>>(emptyList())
    val facts: StateFlow<List<CatFact>> = _facts

    init {
        viewModelScope.launch {
            factsRepository.getAllFavoriteCatFacts().collectLatest { list ->
                _facts.update {
                    list.map { it.toCatFact() }
                }
            }
        }
    }
}