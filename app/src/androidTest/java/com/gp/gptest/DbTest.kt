package com.gp.gptest

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.gp.gptest.managers.DataBaseManager
import com.gp.gptest.model.LocationHistory
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DbTest {

    lateinit var locationBaseManager: DataBaseManager
    @Before
    fun before() {
        locationBaseManager = DataBaseManager.getInstance(InstrumentationRegistry.getTargetContext())
    }

    @Test
    fun testDB() = runBlocking {
        locationBaseManager.insert(LocationHistory(8.0, 8.0))
        delay(100)
        val dataToDel = locationBaseManager.getLast().blockingObserve()

        println("!!!!!!  ======== $dataToDel")


        dataToDel?.let { locationBaseManager.delete(dataToDel) }

        delay(100)
        assertEquals("Test data 8", 8, dataToDel?.lat)

        val dataToDel2 = locationBaseManager.getLast().blockingObserve()

        assertNull(dataToDel2)

    }

    @Test
    fun dbTest() = runBlocking {
        locationBaseManager.insert(LocationHistory())
        delay(100)
        locationBaseManager.insert(LocationHistory(8.0, 8.0))
        delay(100)
        locationBaseManager.insert(LocationHistory(1.0, 2.0))
        delay(100)
        locationBaseManager.insert(LocationHistory(5.0, 5.0))
        delay(1000)
        val list = locationBaseManager.getAll().blockingObserve()
        Assert.assertNotNull(list)
        assertEquals("Check size 4", 4, list?.size)
        println("Size - ${list?.size} $list")

        val dataToDel = list?.get(0)

        println("Last --- ${dataToDel}")
        dataToDel?.let { locationBaseManager.delete(it) }

        delay(2000)
        val list3 = locationBaseManager.getAll().blockingObserve()
        Assert.assertNotNull(list3)
        assertEquals("Check size 3", 3, list3?.size)
        println("Size - ${list3?.size} $list3")

        locationBaseManager.deleteAll()
        delay(500)
        val listEmp = locationBaseManager.getAll().blockingObserve()
        Assert.assertNotNull(listEmp)
        assertEquals("Check size 0", 0, listEmp?.size)
        println("Size - ${listEmp?.size} $listEmp")
    }
}
