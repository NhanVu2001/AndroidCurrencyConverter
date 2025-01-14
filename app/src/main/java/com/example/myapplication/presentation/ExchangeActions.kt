package com.example.myapplication.presentation

sealed interface ExchangeActions {

    data class Input(val value: String): ExchangeActions
    data object Clear: ExchangeActions
    data object Delete: ExchangeActions
    data class SelectedFrom(val index:Int): ExchangeActions
    data class SelectedTo(val index:Int): ExchangeActions
}