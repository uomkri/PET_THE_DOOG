package com.dich.dich2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Repo {
    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean>
        get() = _loginSuccess

    fun setSuccess() {
        _loginSuccess.value = true
        _loginSuccess.value = false
    }
}