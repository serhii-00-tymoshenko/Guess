package com.serhii_00_tymoshenko.guess.ui.board

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.serhii_00_tymoshenko.guess.databinding.FragmentBoardBinding
import com.serhii_00_tymoshenko.guess.repository.Repository
import com.serhii_00_tymoshenko.guess.ui.board.adapters.CardAdapter
import com.serhii_00_tymoshenko.guess.ui.board.viewmodel.BoardViewModel
import com.serhii_00_tymoshenko.guess.ui.board.viewmodel.BoardViewModelProvider

class BoardFragment : Fragment() {
    private var _binding: FragmentBoardBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BoardViewModel
    private lateinit var cardAdapter: CardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()
        val activity = requireActivity()

        initViewModel(activity)
        setupRecyclers(context)
        initObservers()
    }

    private fun initViewModel(activity: FragmentActivity) {
        viewModel = BoardViewModelProvider.getViewModel(activity, Repository.getInstance(4))
    }

    private fun initObservers() {
        viewModel.getBoardOpenedCards().observe(viewLifecycleOwner) { cards ->
            if (cards.size == 2) {
                viewModel.match()
            }
        }
        viewModel.getBoardCards().observe(viewLifecycleOwner) { cards ->
            cardAdapter.submitList(cards.toList())

        }
    }

    private fun setupRecyclers(context: Context) {
        val board = binding.board
        cardAdapter = CardAdapter { card ->
            viewModel.openCard(card)
        }

        board.apply {
            adapter = cardAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}