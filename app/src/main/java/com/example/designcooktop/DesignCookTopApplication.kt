package com.example.designcooktop

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class DesignCookTopApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}