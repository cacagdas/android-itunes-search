package com.cacagdas.itunessearchapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cacagdas.itunessearchapp.vo.ITunesItem

@Database(
    entities = [
        ITunesItem::class],
    version = 3,
    exportSchema = false
)
abstract class ITunesDb : RoomDatabase() {

    abstract fun iTunesDao(): ITunesDao
}
