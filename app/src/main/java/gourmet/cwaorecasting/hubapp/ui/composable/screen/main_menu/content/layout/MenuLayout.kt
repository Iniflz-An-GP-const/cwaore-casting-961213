package gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens

@Composable
fun MenuLayout(
    header: @Composable () -> Unit,
    difficulty: @Composable (Modifier) -> Unit,
    start: @Composable (Modifier) -> Unit,
    highScores: @Composable (Modifier) -> Unit,
    about: @Composable (Modifier) -> Unit,
    exit: @Composable (Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val screenHeight = maxHeight

        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = screenHeight)
                    .padding(
                        horizontal = dimens.screenPadding,
                        vertical = dimens.screenPadding + dimens.sectionGap,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    dimens.contentSpacing,
                    Alignment.CenterVertically
                ),
            ) {
                header()

                difficulty(Modifier.fillMaxWidth())
                start(Modifier.fillMaxWidth())
                highScores(Modifier.fillMaxWidth())

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(dimens.contentSpacing),
                ) {
                    about(Modifier
                        .weight(1f)
                        .fillMaxHeight())
                    exit(Modifier
                        .weight(1f)
                        .fillMaxHeight())
                }

            }
        }
    }
}
