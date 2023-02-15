package ua.blackwind.ui.screens.random_facts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.blackwind.data.cat_facts.CatFactsRepository
import ua.blackwind.data.cat_facts.ICatFactsRepository
import ua.blackwind.data.cat_images.ICatImagesRepository
import ua.blackwind.ui.model.CatFact
import ua.blackwind.ui.model.toCatFact
import ua.blackwind.ui.model.toFavoriteFactDbModel
import javax.inject.Inject

@HiltViewModel
class RandomCatFactsViewModel @Inject constructor(
    private val factsRepository: ICatFactsRepository,
    private val imagesRepository: ICatImagesRepository
): ViewModel() {

    private val _facts = MutableStateFlow<List<CatFact>>(emptyList())
    val facts: StateFlow<List<CatFact>> = _facts
    private var currentCatFactId: Int = 0

    init {
        //TODO add possibility to get and update current fact by id
        //TODO Investigate UI freezes
        viewModelScope.launch(IO) {
            val currentRandomFactIdFlow = factsRepository.getCurrentRandomFactId()
            val lastRandomFactIdFlow = factsRepository.getLastRandomFactId()
            val allRandomFactsFlow = factsRepository.getAllRandomCatFacts()
            val allCatImagesFlow = imagesRepository.getAllCatImagesList()
            combine(
                currentRandomFactIdFlow,
                lastRandomFactIdFlow,
                allRandomFactsFlow,
                allCatImagesFlow
            ) { current, last, facts, images ->
                Pair(
                    Pair(current, last), Pair(facts, images)
                )
            }.collectLatest { (ids, lists) ->
                val current = ids.first
                val last = ids.second
                val factsList = lists.first
                val imageList = lists.second

                currentCatFactId = current

                if (_facts.value.isEmpty() || current == _facts.value.last().id) {
                    _facts.update {
                        val update = factsList.filter { fact ->
                            fact.id in current..if (last - current > 10) current + 10 else last
                        }.map { factModel ->
                            factModel.toCatFact(
                                imageList.find { image -> image.id == factModel.id }?.url ?: ""
                            )
                        }
                        update
                    }
                }
            }
        }
    }

    fun onFavoriteClick() {
        if (currentCatFactId != 0) {
            _facts.value.find { it.id == currentCatFactId }?.let { fact ->
                viewModelScope.launch {
                    factsRepository.insertFavoriteCard(
                        fact.toFavoriteFactDbModel()
                    )
                }
            }
        }
    }

    fun onSwipe(catFact: CatFact) {
        Log.d(
            "SWIPE",
            "swiped fact with id ${catFact.id} facts last id is ${_facts.value.last().id}"
        )
        viewModelScope.launch { factsRepository.insertCurrentRandomFactId(catFact.id + 1) }
    }

    companion object {

    }
}