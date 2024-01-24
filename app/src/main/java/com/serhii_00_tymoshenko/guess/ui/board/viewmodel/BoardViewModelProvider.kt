package com.serhii_00_tymoshenko.guess.ui.board.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.serhii_00_tymoshenko.guess.repository.Repository

class BoardViewModelProvider {
    companion object {
        fun getViewModel(
            owner: ViewModelStoreOwner,
            repository: Repository
        ): BoardViewModel =
            ViewModelProvider(
                owner, BoardViewModelFactory(repository)
            )[BoardViewModel::class.java]
    }
}