package com.example.android_level5_assignment2.Models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "gameTable")
data class Game(
    @ColumnInfo(name = "title")
    var title : String,
    @ColumnInfo(name = "lastUpdated")
    var platform: String,
    @ColumnInfo(name = "text")
    var releaseDate : Date,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Long? = null
) : Parcelable