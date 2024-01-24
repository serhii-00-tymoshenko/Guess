package com.serhii_00_tymoshenko.guess.ui.board.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.serhii_00_tymoshenko.guess.R
import com.serhii_00_tymoshenko.guess.databinding.ItemCardBinding
import com.serhii_00_tymoshenko.hackathon.quiz.models.Card
import com.serhii_00_tymoshenko.hackathon.quiz.models.CardState

class CardAdapter(
    private val callback: (Card) -> Unit,
) : ListAdapter<Card, CardAdapter.CardViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentCard: Card = getItem(position)
        holder.bind(currentCard)
    }

    inner class CardViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card) {
            val cardState = card.state
            when (cardState) {
                CardState.CLOSED -> {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        binding.root.setOnClickListener {
                            callback.invoke(card)
                            binding.textCardNumber.text = card.number.toString()
                        }
                    }
                    binding.textCardNumber.text = "?"
                }

                CardState.OPENED -> {
                    binding.textCardNumber.text = card.number.toString()
                }

                CardState.PAIRED -> {
                    binding.textCardNumber.apply {
                        text = card.number.toString()
                        val color = binding.root.resources.getColor(R.color.green)
                        setTextColor(color)
                    }
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Card, newItem: Card) =
                oldItem == newItem
        }
    }
}