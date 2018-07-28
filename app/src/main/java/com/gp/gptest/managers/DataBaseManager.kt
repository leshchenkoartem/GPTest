package com.gp.gptest.managers

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.gp.gptest.model.LocationHistory
import com.gp.gptest.model.LocationHistoryDataBase
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.util.*

/**
 *  Created by artem on 28.07.2018.
 */
class DataBaseManager {
    private val db: LocationHistoryDataBase

    companion object {
        private var instance: DataBaseManager? = null

        fun getInstance(context: Context): DataBaseManager {
            synchronized(DataBaseManager::class) {
                if (instance == null) {
                    instance = DataBaseManager(context)
                }
                return instance!!
            }
        }
        fun getInstance(): DataBaseManager {
            return instance!!
        }
    }

    constructor(context: Context) {
        db = LocationHistoryDataBase.getInstance(context)
    }

    fun getAll(): MutableLiveData<List<LocationHistory>> {
        val data = MutableLiveData<List<LocationHistory>>()

        launch(UI) {
            val res = async {
                db.locationHistoryDao().getAll()

            }.await()
            data.postValue(res)
        }
        return data
    }

    fun getLast(): LiveData<LocationHistory?> {
        val data = MutableLiveData<LocationHistory?>()
        launch(UI) {
            val d = async {
                val last = db.locationHistoryDao().getLast()
                last?.let {
                    val dif = Date().time - last.time
                    val hours = dif / (60 * 60 * 1000) % 60
                    if(hours>=1)
                        return@async null
                }
                return@async last
            }.await()
            println("D --- $d")
            data.value = d
        }
        return data
    }

    fun insert(locationHistory: LocationHistory) {
        launch {
            db.locationHistoryDao().insert(locationHistory)
        }
    }

    fun deleteAll() {
        launch {
            db.locationHistoryDao().deleteAll()
        }
    }

    fun delete(locationHistory: LocationHistory) {
        launch {
            db.locationHistoryDao().delete(locationHistory)
        }
    }
}