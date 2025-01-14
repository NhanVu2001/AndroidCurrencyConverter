package com.example.myapplication.domain

interface ExchangeRepository {
    suspend fun convert(
        fromCurrency: String,
        toCurrency: String,
        amount: Double
    ): Double

    suspend fun getAllCurrency(): List<Currency>
}