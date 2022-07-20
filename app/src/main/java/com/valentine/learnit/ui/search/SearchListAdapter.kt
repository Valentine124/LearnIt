package com.valentine.learnit.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.valentine.learnit.R
import com.valentine.learnit.internet.Result
import com.valentine.learnit.ui.OnItemClickedListener

class SearchListAdapter(diffCallback: DiffUtil.ItemCallback<Result>): PagingDataAdapter<Result, SearchListAdapter.SearchViewHolder>(diffCallback) {
    private lateinit var mlistener: OnItemClickedListener

    fun setOnItemClickListener(listener: OnItemClickedListener) {
        mlistener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder.create(parent, mlistener)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentCourse = getItem(position)
        holder.bind(
            currentCourse?.title,
            currentCourse
        )
    }

    class SearchViewHolder(itemView: View, listener: OnItemClickedListener) : RecyclerView.ViewHolder(itemView) {
        private val courseTitle = itemView.findViewById<TextView>(R.id.courseTitle)
        private val courseTutor = itemView.findViewById<TextView>(R.id.courseTutor)
        private lateinit var currentCourse: Result

        init {
            itemView.setOnClickListener {
                listener.onItemClickedListener(currentCourse)
            }
        }

        fun bind(title: String?, tutor: Result?) {
            courseTitle.text = title
            tutor?.visible_instructors?.forEach {
                if (it == tutor.visible_instructors.last()) {
                    courseTutor.append(it.title)
                } else {
                    courseTutor.append(it.title + ", ")
                }
            }
            currentCourse = tutor!!
        }

        companion object {
            fun create(parent: ViewGroup, listener: OnItemClickedListener): SearchViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.search_list_item, parent, false)

                return SearchViewHolder(view, listener)
            }
        }

    }
}