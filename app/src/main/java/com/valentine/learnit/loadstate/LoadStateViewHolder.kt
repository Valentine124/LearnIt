package com.valentine.learnit.loadstate

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.valentine.learnit.R
import com.valentine.learnit.databinding.LoadStateBinding

class LoadStateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.load_state, parent, false)
) {
    private val binding = LoadStateBinding.bind(itemView)
    private val progressBar: ProgressBar = binding.progressBar4

    fun bind(loadState: LoadState) {
        progressBar.isVisible = loadState is LoadState.Loading
    }
}