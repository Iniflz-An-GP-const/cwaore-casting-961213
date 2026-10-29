package gourmet.cwaorecasting.hubapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import gourmet.cwaorecasting.hubapp.data.entity.HighScore
import gourmet.cwaorecasting.hubapp.data.model.BestScores
import gourmet.cwaorecasting.hubapp.data.model.Difficulty
import kotlinx.coroutines.flow.Flow

@Dao
interface HighScoreDao {

    @Insert
    suspend fun insert(score: HighScore)

    @Query(
        """
        DELETE FROM high_scores
        WHERE difficulty = :difficulty
            AND id NOT IN (
                SELECT id FROM high_scores
                WHERE difficulty = :difficulty
                ORDER BY moves ASC, time_ms ASC
                LIMIT :limit
            )
            AND id NOT IN (
                SELECT id FROM high_scores
                WHERE difficulty = :difficulty
                ORDER BY time_ms ASC, moves ASC
                LIMIT :limit
            )
        """
    )
    suspend fun trimToTop(difficulty: Difficulty, limit: Int)

    @Transaction
    suspend fun saveRun(score: HighScore) {
        insert(score)
        trimToTop(score.difficulty, TOP_SCORES_PER_METRIC)
    }

    @Query(
        """
        SELECT MIN(moves) AS bestMoves, MIN(time_ms) AS bestTimeMs
        FROM high_scores
        WHERE difficulty = :difficulty
        """
    )
    suspend fun getBest(difficulty: Difficulty): BestScores

    @Query(
        """
        SELECT MIN(moves) AS bestMoves, MIN(time_ms) AS bestTimeMs
        FROM high_scores
        WHERE difficulty = :difficulty
        """
    )
    fun observeBest(difficulty: Difficulty): Flow<BestScores>

    @Query("SELECT * FROM high_scores")
    suspend fun getAll(): List<HighScore>

    @Query("DELETE FROM high_scores")
    suspend fun deleteAll()

    companion object {
        private const val TOP_SCORES_PER_METRIC = 5
    }
}
