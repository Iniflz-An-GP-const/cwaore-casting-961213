package gourmet.cwaorecasting.hubapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import gourmet.cwaorecasting.hubapp.data.model.Difficulty
import java.time.LocalDateTime

@Entity("high_scores")
data class HighScore(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val difficulty: Difficulty,
    val moves: Int,
    @ColumnInfo(name = "time_ms") val timeMs: Long,
    val timestamp: LocalDateTime,
)
