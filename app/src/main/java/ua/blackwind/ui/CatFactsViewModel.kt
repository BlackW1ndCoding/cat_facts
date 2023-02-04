package ua.blackwind.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.blackwind.data.cat_facts.CatFactsRepository
import ua.blackwind.ui.model.CatFact
import javax.inject.Inject

@HiltViewModel
class CatFactsViewModel @Inject constructor(
    private val factsRepository: CatFactsRepository
): ViewModel() {

    private val _facts = MutableStateFlow<List<CatFact>>(emptyList())
    val facts: StateFlow<List<CatFact>> = _facts

    private var factsBuffer = listOf<CatFact>()

    val allFacts = factsRepository.getAllRandomCatFacts()
        .map { list ->
            list.map { model -> CatFact(model.id!!, model.text, "") }.sortedBy { it.id }
        }

    init {
        viewModelScope.launch {
            allFacts.collectLatest { list ->
                factsBuffer = list.take(10)
                if (_facts.value.size < 10) {
                    _facts.update { factsBuffer.take(10) }
                }
            }
        }
        viewModelScope.launch {
            factsRepository.fetchNewRandomCatFacts()
        }
    }

    fun onSwipe(catFact: CatFact) {
        Log.d(
            "SWIPE",
            "swiped fact with id ${catFact.id} facts last id is ${_facts.value.last().id}"
        )
//        if (catFact.id == _facts.value.last().id) {
//            _facts.update { listOf(_facts.value.last()).plus(factsBuffer) }
//        }

        viewModelScope.launch { factsRepository.fetchNewRandomCatFacts() }
        viewModelScope.launch {
            factsRepository.deleteRandomCatFactById(catFact.id)
        }

        if (catFact.id == _facts.value.last().id) {
            viewModelScope.launch {
                _facts.update { factsBuffer }
            }
        }

    }
}