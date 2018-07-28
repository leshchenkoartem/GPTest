package com.gp.gptest

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import java.util.concurrent.CountDownLatch

/**
 *  Created by artem on 26.07.2018.
 */


fun <T> LiveData<T>.blockingObserve(): T? {
    var value: T? = null
    val latch = CountDownLatch(1)
    val innerObserver = Observer<T> {
        value = it
        latch.countDown()
    }
    observeForever(innerObserver)
//    latch.await(2, TimeUnit.SECONDS)
    latch.await()
    return value
}