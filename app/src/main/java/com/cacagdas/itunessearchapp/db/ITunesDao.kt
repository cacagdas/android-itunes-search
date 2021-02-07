package com.cacagdas.itunessearchapp.db

import androidx.paging.DataSource
import androidx.room.*
import com.cacagdas.itunessearchapp.testing.OpenForTesting
import com.cacagdas.itunessearchapp.vo.ITunesItem

@Dao
@OpenForTesting
interface ITunesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<ITunesItem>)

    @Query("SELECT * FROM ITunesItem ORDER BY id DESC")
    fun itemsById(): DataSource.Factory<Int, ITunesItem>

    @Query("DELETE FROM ITunesItem")
    fun deleteItems()
}
