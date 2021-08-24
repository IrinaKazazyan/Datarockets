package com.example.datarockets.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.datarockets.model.BeersListItem


@Dao
interface BeerDao {

    @Query("SELECT * From BEER_LIST_TABLE")
    fun getBeerList(): LiveData<List<BeersListItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBeerListItems(beersListItem: List<BeersListItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRandomBeer(beersListItem: BeersListItem)

    @Query("DELETE FROM BEER_LIST_TABLE")
    fun deleteAllImageItems()

    @Query("SELECT * FROM BEER_LIST_TABLE WHERE id=:id ")
    fun getBeerItemById(id: Int): List<BeersListItem>

    @Update
    fun update(beersListItem: BeersListItem?)
}
