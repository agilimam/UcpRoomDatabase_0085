package com.example.ucp2pam

import android.app.Application
import com.example.ucp2pam.data.dependenciesinjection.ContainerApp

class Apk : Application(){
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()

        containerApp = ContainerApp(this)
    }
}