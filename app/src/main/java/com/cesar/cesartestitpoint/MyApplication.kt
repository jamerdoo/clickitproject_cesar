package com.cesar.cesartestitpoint

import android.app.Application
import android.content.Context

class MyApplication: Application() {

    companion object {
        private lateinit var mIntance: MyApplication

        fun getApp(): MyApplication = mIntance

        fun getAppContext(): Context = mIntance
    }

    override fun onCreate() {
        super.onCreate()
        mIntance = this
    }

}