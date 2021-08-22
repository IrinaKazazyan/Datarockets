package com.example.datarockets.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.datarockets.constants.DATABASE_VERSION
import com.example.datarockets.db.typeConverters.IngredientsConverter
import com.example.datarockets.model.BeersListItem

@Database(entities = [(BeersListItem::class)], version = DATABASE_VERSION, exportSchema = false)
@TypeConverters(IngredientsConverter::class)
abstract class RoomDb : RoomDatabase() {

    abstract fun beerDao(): BeerDao?

    companion object {
        private var INSTANCE: RoomDb? = null

        val migration_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE userinfo ADD COLUMN phone TEXT DEFAULT ''")
            }
        }

        fun getAppDatabase(context: Context): RoomDb? {
            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder<RoomDb>(
                    context.applicationContext, RoomDb::class.java, "AppDBB"
                )
                    .addMigrations(migration_1_2)
                    .allowMainThreadQueries()
                    .build()

            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}