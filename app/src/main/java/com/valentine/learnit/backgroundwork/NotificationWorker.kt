package com.valentine.learnit.backgroundwork

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.valentine.learnit.main.NOTIFY_IMAGE
import com.valentine.learnit.main.NOTIFY_TITLE
import com.valentine.learnit.main.NOTIFY_URL
import kotlinx.coroutines.*

class NotificationWorker(val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val title = inputData.getString(NOTIFY_TITLE)
        val image = inputData.getString(NOTIFY_IMAGE)
        val url = inputData.getString(NOTIFY_URL)
        CoroutineScope(Job() + Dispatchers.IO).launch {
            NotificationHelper(context).createNotification(title, image, url)
        }

        return Result.success()
    }
}