package com.bets.sons.porting.plug.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "recs")
data class Rec(
    @ColumnInfo(name = "score") val score: Int
    ) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0L
}
