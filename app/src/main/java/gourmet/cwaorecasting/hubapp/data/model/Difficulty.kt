package gourmet.cwaorecasting.hubapp.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class Difficulty(
    val rows: Int,
    val columns: Int,
    val previewDurationMs: Long,
) {
    Easy(rows = 4, columns = 3, previewDurationMs = 4_000),
    Medium(rows = 4, columns = 4, previewDurationMs = 6_000),
    Hard(rows = 6, columns = 4, previewDurationMs = 9_000);

    val cardCount: Int get() = rows * columns
    val pairCount: Int get() = cardCount / 2
}
