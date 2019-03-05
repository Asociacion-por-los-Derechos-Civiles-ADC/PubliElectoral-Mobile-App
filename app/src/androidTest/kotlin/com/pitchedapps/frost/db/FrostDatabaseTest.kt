package com.pitchedapps.frost.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class FrostDatabaseTest {

    private lateinit var db: FrostDatabase

    @BeforeTest
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val privateDb = Room.inMemoryDatabaseBuilder(
            context, FrostPrivateDatabase::class.java
        ).build()
        val publicDb = Room.inMemoryDatabaseBuilder(
            context, FrostPublicDatabase::class.java
        ).build()
        db = FrostDatabase(privateDb, publicDb)
    }

    @AfterTest
    fun after() {
        db.close()
    }

    @Test
    fun basic() {
        val cookie = CookieEntity(id = 1234L, name = "testName", cookie = "testCookie")
        runBlocking {
            db.cookieDao().insertCookie(cookie)
            val cookies = db.cookieDao().selectAll()
            assertEquals(listOf(cookie), cookies, "Cookie mismatch")
        }
    }
}