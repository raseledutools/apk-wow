package com.example.appblocker

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class AppBlockerService : AccessibilityService() {

    // এখানে আপনি যেসব অ্যাপ ব্লক করতে চান তার প্যাকেজ নাম দেবেন
    private val blockedApps = listOf(
        "com.facebook.katana", // Facebook
        "com.google.android.youtube", // YouTube
        "com.zhiliaoapp.musically" // TikTok
    )

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString()

            if (packageName != null && blockedApps.contains(packageName)) {
                // অ্যাপ ব্লক লিস্টে থাকলে সাথে সাথে হোম স্ক্রিনে পাঠিয়ে দেবে
                performGlobalAction(GLOBAL_ACTION_HOME)
                Toast.makeText(this, "Focus Mode On! অ্যাপটি ব্লক করা হয়েছে।", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onInterrupt() {
        // সার্ভিস ইন্টারাপ্ট হলে কি হবে
    }
}
