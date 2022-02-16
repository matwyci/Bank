package com.codersmind.mybankapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.codersmind.mybankapp.infrastructure.AppState
import com.codersmind.mybankapp.infrastructure.FakeDatabase
import javax.inject.Inject


class LoginViewModel  @Inject constructor(private val fakeDatabase: FakeDatabase, private val appState: AppState): ViewModel() {
    fun tryLogin(login: String, password: String) : LiveData<LoginState> = liveData {

        val loginUser = fakeDatabase.login(login,password)


        appState.loginState = if(loginUser == null) LoginState.FAIL else LoginState.SUCCESS


        appState.user = loginUser


        emit(appState.loginState)
    }
}