package com.bets.sons.porting

import android.app.Application
import androidx.core.app.ActivityCompat
import com.onesignal.OneSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppCl: Application() {
    val ONESIGNAL_APP_ID = "cbc1d1eb-e583-4f62-b52a-50ed55b85a11"
    override fun onCreate() {
        super.onCreate()
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID)

//        CoroutineScope(Dispatchers.IO).launch {
//            OneSignal.Notifications.requestPermission(true)
//        }

    }
}