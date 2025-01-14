package com.example.myapplication.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.domain.ConvertUserCase
import com.example.myapplication.domain.ExchangeRepository
import kotlinx.coroutines.launch

class MainView(
    private val convertUserCase: ConvertUserCase,
    private val exchangeRepository: ExchangeRepository
) : ViewModel() {

    var state by mutableStateOf(ExchangeState())
        private set

    init {
        viewModelScope.launch {
            convert()
            state = state.copy(
                allCurrency = exchangeRepository.getAllCurrency()
            )
        }
    }


    fun onAction(action: ExchangeActions) {
        when (action) {
            ExchangeActions.Clear -> {
                state = state.copy(
                    amount = "",
                    result = "",
                )
            }

            ExchangeActions.Delete -> {
                if (state.amount.isBlank()) return
                state = state.copy(
                    amount = state.amount.dropLast(1)
                )
                convert()
            }

            is ExchangeActions.Input -> {
                state = state.copy(
                    amount = state.amount + action.value
                )
                convert()
            }

            is ExchangeActions.SelectedFrom -> {
                state = state.copy(
                    from = state.allCurrency[action.index]
                )
                convert()
            }

            is ExchangeActions.SelectedTo -> {
                state = state.copy(
                    to = state.allCurrency[action.index]
                )
                convert()
            }
        }
    }

    private fun convert() {
        viewModelScope.launch {
            state = state.copy(
                result = convertUserCase(
                    fromCurrency = state.from.code,
                    toCurrency = state.to.code,
                    amount = state.amount
                )
            )
        }
    }
}