package ua.blackwind.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
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
        //TODO find bug with loading new facts
        Log.d("FACTS", " getting more facts from db for $current and $last")
        viewModelScope.launch(IO) {

            val list = factsRepository.getRandomFactsListByIdRange(
                current,
                if (last - current > 10) current + 10 else last
            ).map { it.toCatFact() }

            _facts.update { list }
        }
    }

    fun onSwipe(catFact: CatFact) {
        Log.d(
            "SWIPE",
            "swiped fact with id ${catFact.id} facts last id is ${_facts.value.last().id}"
        )
        viewModelScope.launch { factsRepository.insertCurrentRandomFactId(catFact.id + 1) }

        if (catFact.id + 1 == _facts.value.last().id) {
            Log.d(
                "SWIPE",
                "attempting loading facts }"
            )
            getMoreCatFactsFromDB(
                catFact.id + 1,
                catFact.id + 10
            )
        }
    }
}