package ua.blackwind.ui.screens.random_facts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.blackwind.data.cat_facts.CatFactsRepository
import ua.blackwind.ui.model.CatFact
import ua.blackwind.ui.model.toCatFact
import ua.blackwind.ui.model.toFavoriteFactDbModel
import javax.inject.Inject

@HiltViewModel
class RandomCatFactsViewModel @Inject constructor(
    private val factsRepository: CatFactsRepository
): ViewModel() {

    private val _facts = MutableStateFlow<List<CatFact>>(emptyList())
    val facts: StateFlow<List<CatFact>> = _facts
    private var currentCatFact: CatFact? = null

    init {
        //TODO add possibility to get and update current fact by id
        //TODO Investigate UI freezes
        viewModelScope.launch(IO) {
            factsRepository.getCurrentRandomFactId()
                .combine(factsRepository.getLastRandomFactId()) { current, last ->
                    Pair(current, last)
                }.combine(factsRepository.getAllRandomCatFacts()) { pair, list ->
                    Pair(pair, list)
                }.collectLatest { (pair, list) ->

                    val current = pair.first
                    val last = pair.second
                    //currentCatFact = factsRepository.get

                    if (_facts.value.isEmpty() || current == _facts.value.last().id) {
                        _facts.update {
                            val update = list.filter { fact ->
                                fact.id in current..if (last - current > 10) current + 10 else last
                            }.map { it.toCatFact() }
                            update
                        }
                    }
                }
        }
    }

    fun onFavoriteClick() {
        currentCatFact?.let { fact ->
            viewModelScope.launch {
                factsRepository.insertFavoriteCard(
                    fact.toFavoriteFactDbModel()
                )
            }

        }
    }

    fun onSwipe(catFact: CatFact) {
        Log.d(
            "SWIPE",
            "swiped fact with id ${catFact.id} facts last id is ${_facts.value.last().id}"
        )
        currentCatFact = catFact
        viewModelScope.launch { factsRepository.insertCurrentRandomFactId(catFact.id + 1) }
    }

    companion object {

    }
}