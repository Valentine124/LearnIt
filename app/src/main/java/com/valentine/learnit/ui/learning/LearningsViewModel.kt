package com.valentine.learnit.ui.learning

import androidx.lifecycle.ViewModel
import com.valentine.learnit.db.RecentCourses

class LearningsViewModel : ViewModel() {
    val visitedList: MutableList<RecentCourses> = mutableListOf()

}
