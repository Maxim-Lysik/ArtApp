package com.example.artapp

import RetroRepository
import android.app.Application

class RetroIntentApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        RetroRepository.initialize(this)
    }

}