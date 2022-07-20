package com.valentine.learnit.db

import com.valentine.learnit.internet.VisibleInstructor


data class RecentCourses (
    val tracking_id: String? = null,
    val id: Int? = null,
    val title: String? = null,
    val image_480x270: String? = null,
    val price: String? = null,
    val url: String? = null,
    val visible_instructors: List<VisibleInstructor?>? = null
    )