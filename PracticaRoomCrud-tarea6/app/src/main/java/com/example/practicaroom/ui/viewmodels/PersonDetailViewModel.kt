package com.example.practicaroom.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicaroom.db.models.Pet
import com.example.practicaroom.repositories.PersonRepository
import kotlinx.coroutines.launch

class PersonDetailViewModel : ViewModel() {
    private val _person: MutableLiveData<Pet> = MutableLiveData(null)
    val person: LiveData<Pet> = _person
    private val _hasErrorSaving: MutableLiveData<Boolean> = MutableLiveData(false)
    val hasErrorSaving: LiveData<Boolean> = _hasErrorSaving

    private val _personSaved = MutableLiveData<Pet>(null)
    val personSaved: LiveData<Pet> = _personSaved

    fun loadPerson(context: Context, id: Int) {
        viewModelScope.launch {
            _person.postValue(PersonRepository.getPetById(context, id))
        }
    }

    fun savePerson(context: Context, person: Pet) {
        viewModelScope.launch {
            try {
                val id = PersonRepository.savePerson(context, person)
                person.id = id
                _personSaved.postValue(person)
            } catch (e: Exception) {
                e.printStackTrace()
                _hasErrorSaving.postValue(true)
            }
        }
    }
}