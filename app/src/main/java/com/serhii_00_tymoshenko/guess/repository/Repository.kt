package com.serhii_00_tymoshenko.guess.repository

import com.serhii_00_tymoshenko.guess.models.Card
import com.serhii_00_tymoshenko.guess.models.CardState

class Repository(private val numberOfPairs: Int) {
    private val boardCards by lazy {
        generateRandomSequence(numberOfPairs).toMutableList()
    }

    private fun generateRandomSequence(numberOfPairs: Int): List<Card> {
        val tempCards = initList(numberOfPairs)
        return tempCards.shuffled()
    }

    private fun initList(numberOfPairs: Int): List<Card> {
        val tempCards = mutableListOf<Card>()

        for (i in 1..numberOfPairs) {
            repeat(2) {
                tempCards.add(Card(i, CardState.CLOSED))
            }
        }

        return tempCards
    }

    fun getCards(): List<Card> = boardCards

    fun changeState(card: Card, state: CardState) {
        val position = boardCards.indexOf(card)
        val tempCard = boardCards[position]
        boardCards.remove(tempCard)
        boardCards.add(position, tempCard.copy(state = state))
    }

    companion object {
        var instance: Repository? = null

        fun getInstance(numberOfPairs: Int) =
            instance ?: Repository(numberOfPairs).also { instance = it }
    }
}