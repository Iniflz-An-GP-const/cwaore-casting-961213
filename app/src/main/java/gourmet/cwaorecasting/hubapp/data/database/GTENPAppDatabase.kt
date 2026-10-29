package gourmet.cwaorecasting.hubapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gourmet.cwaorecasting.hubapp.data.database.converter.Converters
import gourmet.cwaorecasting.hubapp.data.database.dao.HighScoreDao
import gourmet.cwaorecasting.hubapp.data.entity.HighScore

@Database(
    entities = [HighScore::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GTENPAppDatabase : RoomDatabase() {

    abstract fun highScoreDao(): HighScoreDao
}
