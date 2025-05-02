package com.alekseilomain.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alekseilomain.data.database.AppDatabase
import com.alekseilomain.data.database.ContactDao
import com.alekseilomain.data.database.model.ContactEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContactDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: ContactDao

    @Before
    fun setup() {
        val ctx = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(ctx, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.contactDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insert_and_queryAllFlow_returnsInsertedOrderedByIsManualDescThenIdDesc() = runBlocking {
        val e1 = ContactEntity(id = 0, lastName = "A", email = "a@a.com", isManual = false)
        val e2 = ContactEntity(id = 0, lastName = "B", email = "b@b.com", isManual = true)
        val e3 = ContactEntity(id = 0, lastName = "C", email = "c@c.com", isManual = false)

        dao.insert(e1)
        dao.insert(e2)
        dao.insert(e3)

        val all = dao.getAllFlow().first()
        // manual=true должен быть первым, а остальные — по возрастанию ID (Room auto-increments)
        Assert.assertEquals(listOf(e2.copy(id = 2), e1.copy(id = 1), e3.copy(id = 3)), all)
    }


}