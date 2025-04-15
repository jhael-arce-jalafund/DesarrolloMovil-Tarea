package com.example.practicaroom.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicaroom.db.models.Pet
import com.example.practicaroom.repositories.PersonRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _personList: MutableLiveData<MutableList<Pet>> = MutableLiveData(mutableListOf())
    val personList: LiveData<MutableList<Pet>> = _personList

    fun loadData(context: Context) {
        viewModelScope.launch {
            _personList.postValue(PersonRepository.getPersonList(context) as MutableList<Pet>)
        }
    }

    fun deletePerson(context: Context, person: Pet) {
        viewModelScope.launch {
            Log.d("Pet", "Deleting person with id ${person.id}")
            PersonRepository.deletePerson(context, person)
        }
    }
}