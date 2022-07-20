package com.valentine.learnit.ui.healthfitness

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.valentine.learnit.R
import com.valentine.learnit.internet.Result
import com.valentine.learnit.ui.OnItemClickedListener

class HealthAndFitnessAdapter(diffCallback: DiffUtil.ItemCallback<Result>) : PagingDataAdapter<Result, HealthAndFitnessAdapter.HealthAndFitnessViewHolder>(diffCallback) {
    private lateinit var mlistener: OnItemClickedListener

    fun setOnItemClickListeners(listener: OnItemClickedListener) {
        mlistener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthAndFitnessViewHolder {
        return HealthAndFitnessViewHolder.create(parent, mlistener)
    }

    override fun onBindViewHolder(holder: HealthAndFitnessViewHolder, position: Int) {
        val currentCourse = getItem(position)
        holder.bind(currentCourse?.image_480x270, currentCourse?.title,
            currentCourse, currentCourse?.price)
    }

    class HealthAndFitnessViewHolder(itemView: View, listener: OnItemClickedListener) : RecyclerView.ViewHolder(itemView) {
        private val designCourseImg = itemView.findViewById<ImageView>(R.id.designCourseImage)
        private val designCourseTitle = itemView.findViewById<TextView>(R.id.design_course_title)
        private val designCourseTutor = itemView.findViewById<TextView>(R.id.design_course_tutor)
        private val designCourseFee = itemView.findViewById<TextView>(R.id.design_course_fee)
        private lateinit var currentCourse: Result

        init {
            itemView.setOnClickListener {
                listener.onItemClickedListener(currentCourse)
            }
        }

        fun bind(img: String?, title: String?, tutor: Result?, fee: String?) {
            designCourseImg.load(img)
            designCourseTitle.text = title
            tutor?.visible_instructors?.forEach{
                if (it == tutor.visible_instructors.last()) {
                    designCourseTutor.append(it.title)
                } else {
                    designCourseTutor.append(it.title + ", ")
                }
            }
            designCourseFee.text = fee
            currentCourse = tutor!!
        }

        companion object {
            fun create(parent: ViewGroup, listener: OnItemClickedListener): HealthAndFitnessViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)

                return HealthAndFitnessViewHolder(view, listener)
            }
        }

    }
}