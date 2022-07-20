package com.valentine.learnit.ui.learning

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.valentine.learnit.R
import com.valentine.learnit.db.RecentCourses

class LearningAdapter: ListAdapter<RecentCourses, LearningAdapter.LearningViewHolder>(LearningsComparator()) {
    private lateinit var mlistener: OnLearningsClickListener

    fun setOnLearningsClickListener(listener: OnLearningsClickListener) {
        mlistener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningViewHolder {
        return LearningViewHolder.create(parent, mlistener)
    }

    override fun onBindViewHolder(holder: LearningViewHolder, position: Int) {
        val currentCourse = getItem(position)
        currentCourse?.let {
            holder.bind(
                it.title, it.image_480x270,
                currentCourse.price, currentCourse)
        }
    }

    class LearningViewHolder(itemView: View, listener: OnLearningsClickListener): RecyclerView.ViewHolder(itemView) {
        private val courseTitle: TextView = itemView.findViewById(R.id.design_course_title)
        private val courseTutors: TextView = itemView.findViewById(R.id.design_course_tutor)
        private val courseImage: ImageView = itemView.findViewById(R.id.designCourseImage)
        private val coursePrice: TextView = itemView.findViewById(R.id.design_course_fee)
        private lateinit var currentCourse: RecentCourses

        init {
            itemView.setOnClickListener {
                listener.onLearningsClick(currentCourse)
            }
        }

        fun bind(title: String?, image: String?, price: String?, tutor: RecentCourses?){
            courseTitle.text = title
            courseImage.load(image)
            coursePrice.text = price
            tutor?.visible_instructors?.forEach{
                if (it == tutor.visible_instructors.last()) {
                    courseTutors.append(it?.title)
                } else {
                    courseTutors.append(it?.title + ", ")
                }
            }

            currentCourse = tutor!!

        }

        companion object {
            fun create(parent: ViewGroup, listener: OnLearningsClickListener): LearningViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
                return LearningViewHolder(view, listener)
            }
        }
    }
}

interface OnLearningsClickListener {
    fun onLearningsClick(recent: RecentCourses)
}

class LearningsComparator : DiffUtil.ItemCallback<RecentCourses>() {
    override fun areItemsTheSame(oldItem: RecentCourses, newItem: RecentCourses): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RecentCourses, newItem: RecentCourses): Boolean {
        return oldItem.tracking_id == newItem.tracking_id
    }
}
