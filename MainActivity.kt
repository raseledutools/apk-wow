package com.example.appblocker

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAccessibility = findViewById<Button>(R.id.btnAccessibility)
        val tvStatus = findViewById<TextView>(R.id.tvStatus)

        btnAccessibility.setOnClickListener {
            if (!isAccessibilityServiceEnabled()) {
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val tvStatus = findViewById<TextView>(R.id.tvStatus)
        val btnAccessibility = findViewById<Button>(R.id.btnAccessibility)

        if (isAccessibilityServiceEnabled()) {
            tvStatus.text = "Status: Active & Protecting"
            tvStatus.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
            btnAccessibility.text = "All Permissions Granted"
            btnAccessibility.isEnabled = false
        } else {
            tvStatus.text = "Status: Inactive (Permission Needed)"
            tvStatus.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
            btnAccessibility.isEnabled = true
        }
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val expectedComponentName = "$packageName/${AppBlockerService::class.java.canonicalName}"
        val enabledServicesSetting = Settings.Secure.getString(contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES) ?: return false
        val colonSplitter = TextUtils.SimpleStringSplitter(':')
        colonSplitter.setString(enabledServicesSetting)
        while (colonSplitter.hasNext()) {
            if (colonSplitter.next().equals(expectedComponentName, ignoreCase = true)) {
                return true
            }
        }
        return false
    }
}
