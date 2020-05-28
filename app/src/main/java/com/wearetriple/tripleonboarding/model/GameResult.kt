package com.wearetriple.tripleonboarding.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "GameResult")
data class GameResult(
    @ColumnInfo(name = "game")
    val game: Game,

    @ColumnInfo(name = "highscore")
    var highscore: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
) : Parcelable