package com.codersmind.mybankapp.entities

import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList


data class LoginEntity(
    val userId: UUID,
    val userName: String,
    val userLogin: String,
    val password: String,
    val accountNumber: String,
    var moneyAmount: BigDecimal,
    val history: ArrayList<String> = ArrayList()
)