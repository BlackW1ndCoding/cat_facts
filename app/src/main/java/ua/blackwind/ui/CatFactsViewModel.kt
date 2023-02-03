package ua.blackwind.ui

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
import ua.blackwind.data.db.model.RandomCatFactDbModel
import ua.blackwind.ui.model.CatFact
import javax.inject.Inject

@HiltViewModel
class CatFactsViewModel @Inject constructor(
    private val factsRepository: CatFactsRepository
):
    ViewModel() {
    private val _currentFact = MutableStateFlow<CatFact?>(null)
    val currentFact: StateFlow<CatFact?> = _currentFact
    private val _nextFact = MutableStateFlow<CatFact?>(null)
    val nextFact: StateFlow<CatFact?> = _nextFact

    private var currentFactIndex = 0
    private var currentFactsList = listOf<CatFact>()
    private var updatedFactsList = listOf<RandomCatFactDbModel>()

    init {
        viewModelScope.launch { factsRepository.fetchNewRandomCatFacts() }
        viewModelScope.launch(IO) {
            factsRepository.getAllRandomCatFacts().collectLatest {
                updatedFactsList = it
                if (currentFactsList.size < 2 && updatedFactsList.isNotEmpty()) {
                    currentFactsList = updatedFactsList.map { CatFact(it.id!!, it.text, "") }
                    _currentFact.update { currentFactsList[0] }
                }

            }
            _currentFact.update { currentFactsList[0] }
//            _nextFact.update {
//                try {
//                    currentFactsList[1]
//                } catch (exception: Exception) {
//                    null
//                }
//            }
        }
    }
}