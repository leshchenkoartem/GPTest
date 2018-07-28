package com.gp.gptest.view.history

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.gp.gptest.model.LocationHistory
import com.gp.gptest.model.LocationHistoryDataBase

/**
 *  Created by artem on 28.07.2018.
 */
class HistoryVM() : ViewModel() {
    val lacationHistoryList: LiveData<PagedList<LocationHistory>> =
            LivePagedListBuilder(LocationHistoryDataBase.getInstance().locationHistoryDao().getAllByData(), 20)
                    .build()
}