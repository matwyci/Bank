package com.codersmind.mybankapp.ui.dashboard

import com.codersmind.mybankapp.entities.LoginEntity
import com.codersmind.mybankapp.infrastructure.AppState
import com.codersmind.mybankapp.infrastructure.FakeDatabase
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val fakeDatabase: FakeDatabase,
    private val appState: AppState
) {


    val loginName
        get() = appState.user?.userName

    val accountNumber
        get() = appState.user?.accountNumber

    val balance
        get() = "${appState.user?.moneyAmount.toString()}$"

    private val balanceValue
        get() = appState.user?.moneyAmount

    val history
        get() = appState.user?.history ?: emptyList()

    fun validate(accountNumber: String, money: String): ValidationState {
        val moneyBigDecimal = tryParseMoney(money)
        val accountUser =
            fakeDatabase.findUserByAccountNumber(accountNumber)
        return when {
            accountNumber.isBlank() || money.isBlank() -> ValidationState.FAILED_FILL_DATA

            moneyBigDecimal == BigDecimal.ZERO -> ValidationState.FAILED_MONEY

            moneyBigDecimal > balanceValue -> ValidationState.FAILED_SMALL_BALANCE

            accountUser == null -> ValidationState.FAILED_NOT_FOUND_ACCOUNT

            accountNumber.replace(" ", "") == appState.user?.accountNumber?.replace(" ", "")
                .orEmpty() -> ValidationState.FAILED_SELF_SEND

            else -> ValidationState.OK
        }
    }

    private fun tryParseMoney(money: String): BigDecimal = try {
        BigDecimal(money)
    } catch (e: Exception) {
        BigDecimal.ZERO
    }

    fun validationStateToMessage(state: ValidationState): String = when (state) {
        ValidationState.OK -> ""
        ValidationState.FAILED_FILL_DATA -> "Fill account number and amount of money to send!"
        ValidationState.FAILED_MONEY -> "Fill money!"
        ValidationState.FAILED_SMALL_BALANCE -> "Money value should be lower than your current balance!"
        ValidationState.FAILED_NOT_FOUND_ACCOUNT -> "Cannot find account with this number!"
        ValidationState.FAILED_SELF_SEND -> "Cannot send money to your account!"
    }
    fun sendMoney(accountNumber: String, money: String) =
        fakeDatabase.findUserByAccountNumber(accountNumber)?.parseMoneyAndSend(money)

    private fun LoginEntity.parseMoneyAndSend(money: String) = tryParseMoney(money).let { money ->
        this.moneyAmount += money;


        appState.user?.let { currentUser ->
            currentUser.moneyAmount -= money;


            currentUser.history.add("Sent $money to ${this.userName}(${this.accountNumber}), from account ${currentUser.accountNumber} at ${Calendar.getInstance().time}")


            this.history.add("Recived $money from ${currentUser.userName}(${this.accountNumber}) at ${Calendar.getInstance().time}")
            currentUser
        }
    }
}


enum class ValidationState {
    OK,
    FAILED_FILL_DATA,
    FAILED_MONEY,
    FAILED_NOT_FOUND_ACCOUNT,
    FAILED_SMALL_BALANCE,
    FAILED_SELF_SEND
}