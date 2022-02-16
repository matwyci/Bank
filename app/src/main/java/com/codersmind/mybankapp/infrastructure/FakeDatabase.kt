package com.codersmind.mybankapp.infrastructure

import androidx.lifecycle.LiveData
import com.codersmind.mybankapp.entities.LoginEntity
import java.math.BigDecimal
import java.util.*


object FakeDatabase {

    private val users = listOf(

        LoginEntity(
            userId = UUID.randomUUID(),
            userName = "Marian Test",
            userLogin = "l1",
            password = "",
            accountNumber = "0000 1111 5555 22222",
            moneyAmount = BigDecimal.valueOf(10000)
        ),

        LoginEntity(
            userId = UUID.randomUUID(),
            userName = "Adrian Test",
            userLogin = "l2",
            password = "",
            accountNumber = "9999 1111 5555 22222",
            moneyAmount = BigDecimal.valueOf(50)
        )
    )

    fun login(login: String, password: String) =
        users.firstOrNull { dbUser -> dbUser.userLogin == login && dbUser.password == password }


    fun findUserByAccountNumber(accountNumber: String) =
        users.firstOrNull {
            it.accountNumber.replace(
                " ",
                ""
            ) == accountNumber.replace(" ", "")
        }
}