package com.serhii_00_tymoshenko.guess.models

import java.util.UUID

data class Card(
    val number: Int,
    val state: CardState,
    val id: String = UUID.randomUUID().toString()
)

