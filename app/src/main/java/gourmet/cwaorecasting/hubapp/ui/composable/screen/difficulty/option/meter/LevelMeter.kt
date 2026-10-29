package gourmet.cwaorecasting.hubapp.ui.composable.screen.difficulty.option.meter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gourmet.cwaorecasting.hubapp.ui.composable.screen.difficulty.option.DifficultyOptionDefaults
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens

private val FlameSize = 22.dp

@Composable
fun LevelMeter(
    level: Int,
    levelCount: Int,
    modifier: Modifier = Modifier,
) {
    val color = LocalContentColor.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.tightSpacing),
    ) {
        repeat(levelCount) { index ->
            Icon(
                imageVector = Icons.Filled.LocalFireDepartment,
                contentDescription = null,
                tint = if (index < level) color else color.copy(alpha = DifficultyOptionDefaults.INACTIVE_ALPHA),
                modifier = Modifier.size(FlameSize),
            )
        }
    }
}
