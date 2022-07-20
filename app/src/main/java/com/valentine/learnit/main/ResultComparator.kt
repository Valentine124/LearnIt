package com.valentine.learnit.main

import androidx.recyclerview.widget.DiffUtil
import com.valentine.learnit.internet.Result

object ResultComparator : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }
}