package br.com.fiap25mob.mbamobile.presentation.guitarlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap25mob.mbamobile.data.db.GuitarsEntity
import br.com.fiap25mob.mbamobile.repository.GuitarsRepository
import kotlinx.coroutines.launch

class GuitarListViewModel(private val repository: GuitarsRepository) : ViewModel() {

    private val _allGuitarsEvent = MutableLiveData<List<GuitarsEntity>>()
    val allGuitarsEvent: LiveData<List<GuitarsEntity>> get() = _allGuitarsEvent

    fun getGuitars() = viewModelScope.launch {
        _allGuitarsEvent.postValue(repository.getAllGuitars())
    }
}