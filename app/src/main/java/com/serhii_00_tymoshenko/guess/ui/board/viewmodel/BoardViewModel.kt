package com.serhii_00_tymoshenko.guess.ui.board.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhii_00_tymoshenko.guess.repository.Repository
import com.serhii_00_tymoshenko.hackathon.quiz.models.Card
import com.serhii_00_tymoshenko.hackathon.quiz.models.CardState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BoardViewModel(private val repository: Repository) : ViewModel() {
    private val _cards = MutableLiveData<List<Card>>(listOf())
    private val _openedCards = MutableLiveData<List<Card>>(listOf())

    fun getBoardCards(): LiveData<List<Card>> = _cards
    fun getBoardOpenedCards(): LiveData<List<Card>> = _openedCards

    init {
        getCards()
    }

    private fun getCards() {
        val tempCards = repository.getCards()
        _cards.value = tempCards
    }

    fun match() {
        val openedCards = _openedCards.value!!

        viewModelScope.launch(Dispatchers.IO) {
            if (openedCards[0].number == openedCards[1].number) {
                openedCards.forEach { card -> changeState(card, CardState.PAIRED) }
            } else {
                openedCards.forEach { card -> changeState(card, CardState.CLOSED) }
            }

            delay(500)

            withContext(Dispatchers.Main) {
                reset()
                getCards()
            }
        }
    }

    private fun reset() {
        _openedCards.value = listOf()
    }

    fun openCard(card: Card) {
        changeState(card, CardState.OPENED)

        val tempCards = _openedCards.value!!.toMutableList()
        tempCards.add(card.copy(state = CardState.OPENED))
        _openedCards.value = tempCards
    }

    private fun changeState(card: Card, state: CardState) {
        repository.changeState(card, state)
    }
}