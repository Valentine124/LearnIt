package com.valentine.learnit.backgroundwork

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.valentine.learnit.R
import com.valentine.learnit.dashboard.CourseDashboardActivity
import com.valentine.learnit.main.BASE_URL
import com.valentine.learnit.main.COURSE_URL_STRING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

class NotificationHelper(private val context: Context) {
    private val notificationId = 1

    fun createNotification(title: String?, image: String?, url: String?) {
        createNotificationChannel()

        val intent = Intent(context, CourseDashboardActivity::class.java)
        intent.putExtra(COURSE_URL_STRING, BASE_URL + url)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)

        createNotificationAndBitmap(builder, image, title, pendingIntent)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationAndBitmap(builder: NotificationCompat.Builder, imageUrl: String?,
                                    title: String?, pendingIntent: PendingIntent) = runBlocking {
        val url = URL(imageUrl)

        withContext(Dispatchers.IO) {
            try {
                val input = url.openStream()
                BitmapFactory.decodeStream(input)
            } catch (e: IOException) {
                null
            }
        }?.let { bitmap ->
            builder.apply {
                setSmallIcon(R.drawable.learnit_logo)
                setContentTitle("Start learning a course you visited")
                setContentText(title)
                setLargeIcon(bitmap)
                setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
                    .bigLargeIcon(null))
                priority = NotificationCompat.PRIORITY_DEFAULT
                setContentIntent(pendingIntent)
                setAutoCancel(true)
            }
        }
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, "notify", importance).apply {
                description = "text description"
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object{private const val CHANNEL_ID = "id.learnIt.notification"}
}