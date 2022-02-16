package com.codersmind.mybankapp.infrastructure

import com.codersmind.mybankapp.entities.LoginEntity
import com.codersmind.mybankapp.ui.login.LoginState
import javax.inject.Singleton

@Singleton

data class AppState(var loginState: LoginState = LoginState.DEFAULT, var user: LoginEntity? = null)