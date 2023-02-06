package ua.blackwind.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.blackwind.data.cat_facts.CatFactsRepository
import ua.blackwind.data.db.model.RandomCatFactDbModel
import ua.blackwind.ui.model.CatFact
import ua.blackwind.ui.model.toCatFact
import javax.inject.Inject

@HiltViewModel
class CatFactsViewModel @Inject constructor(
    private val factsRepository: CatFactsRepository
): ViewModel() {

    private val _facts = MutableStateFlow<List<CatFact>>(emptyList())
    val facts: StateFlow<List<CatFact>> = _facts

    init {
        viewModelScope.launch(IO) {
            val currentFact = factsRepository.getCurrentRandomFactId()
            getMoreCatFactsFromDB(currentFact, currentFact + 10)
        }

        viewModelScope.launch(IO) {
            factsRepository.getLastRandomFactId().collectLatest { id ->
                Log.d("FACTS", "Collecting id $id")
                id?.let { last ->
                    if (_facts.value.isEmpty()) {
                        val current = factsRepository.getCurrentRandomFactId()
                        Log.d("FACTS", "Current id $current")
                        getMoreCatFactsFromDB(current, last)
                    }
                }
            }
        }
    }

    private fun getMoreCatFactsFromDB(
        current: Int,
        last: Int
    ) {
        Log.d("FACTS"," getting more facts from db for $current and $last")
        viewModelScope.launch(IO) {
            _facts.update {
                factsRepository.getRandomFactsListByIdRange(
                    current,
                    if (last - current > 10) current + 10 else last
                ).map { it.toCatFact() }
            }
        }
    }

    fun onSwipe(catFact: CatFact) {
        Log.d(
            "SWIPE",
            "swiped fact with id ${catFact.id} facts last id is ${_facts.value.last().id}"
        )
        viewModelScope.launch { factsRepository.insertCurrentRandomFactId(catFact.id) }

        if (catFact.id + 1 == _facts.value.last().id) {
            Log.d(
                "SWIPE",
                "attempting loading facts }"
            )
            getMoreCatFactsFromDB(
                catFact.id,
                catFact.id + 10
            )
        }
    }
}