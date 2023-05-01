package br.com.fiap25mob.mbamobile.presentation.guitars

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap25mob.mbamobile.R
import br.com.fiap25mob.mbamobile.repository.GuitarsRepository
import kotlinx.coroutines.launch

class GuitarsViewModel(private val repository: GuitarsRepository) : ViewModel() {

    private val _guitarStateEventData = MutableLiveData<GuitarState>()
    val guitarStateEventData: LiveData<GuitarState> get() = _guitarStateEventData

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int> get() = _messageEventData

    private fun includeGuitar(brand: String, model: String) = viewModelScope.launch {
        try {
            if (brand == "" || model == "") {
                _messageEventData.value = R.string.crud_insert_missing

            } else {
                val id = repository.insertGuitar(brand, model)
                if (id > 0) {
                    _guitarStateEventData.value = GuitarState.Included
                    _messageEventData.value = R.string.crud_insert_success
                }
            }
        } catch (ex: Exception) {
            _messageEventData.value = R.string.crud_insert_error
            Log.e(TAG, ex.toString())
        }
    }

    private fun updateGuitar(id: Long, brand: String, model: String) = viewModelScope.launch {
        try {
            repository.updateGuitar(id, brand, model)
            _guitarStateEventData.value = GuitarState.Updated
            _messageEventData.value = R.string.crud_update_success
        } catch (ex: Exception) {
            _messageEventData.value = R.string.crud_update_error
            Log.e(TAG, ex.toString())
        }
    }

    fun includeUpdateGuitar(brand: String, model: String, id: Long = 0) {
        if (id > 0) {
            updateGuitar(id, brand, model)
        } else {
            includeGuitar(brand, model)
        }
    }

    fun deleteGuitar(id: Long) = viewModelScope.launch {
        try {
            if (id > 0) {
                repository.deleteGuitar(id)
                _guitarStateEventData.value = GuitarState.Deleted
                _messageEventData.value = R.string.crud_delete_success
            }
        } catch (ex: Exception) {
            _messageEventData.value = R.string.crud_delete_error
            Log.e(TAG, ex.toString())
        }
    }

    companion object {
        private val TAG = GuitarsViewModel::class.java.simpleName
    }

    sealed class GuitarState {
        object Included : GuitarState()
        object Updated : GuitarState()
        object Deleted : GuitarState()
    }
}