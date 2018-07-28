package com.gp.gptest.app

import android.app.Application
import com.gp.gptest.managers.DataBaseManager

/**
 *  Created by artem on 28.07.2018.
 */
class App:Application() {
    override fun onCreate() {
        super.onCreate()
        DataBaseManager.getInstance(applicationContext)
    }

}