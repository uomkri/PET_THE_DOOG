package com.dich.dich2.screens.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val _counter = MutableLiveData(0)
    val counter: LiveData<Int>
        get() = _counter

    private val _lastId = MutableLiveData<String>()
    val lastId: LiveData<String>
        get() = _lastId

    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun generatePetId(): String {
        val randomString = (1..20)
            .map { kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("");

        _lastId.value = randomString
        return randomString
    }

    fun incrementCounter() {
        _counter.value = _counter.value?.plus(1)
    }
}