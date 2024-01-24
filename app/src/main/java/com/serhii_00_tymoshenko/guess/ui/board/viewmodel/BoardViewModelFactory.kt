package com.serhii_00_tymoshenko.guess.ui.board.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.serhii_00_tymoshenko.guess.repository.Repository

class BoardViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BoardViewModel::class.java)) {
            return BoardViewModel(repository) as T
        }

        throw Exception("Wrong ViewModel")
    }
}