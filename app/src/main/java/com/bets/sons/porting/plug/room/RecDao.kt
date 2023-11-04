package com.bets.sons.porting.plug.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRec(rec: Rec)

    @Query("SELECT * FROM recs ORDER BY id DESC LIMIT 5")
    fun getRecs(): Flow<List<Rec>>
}