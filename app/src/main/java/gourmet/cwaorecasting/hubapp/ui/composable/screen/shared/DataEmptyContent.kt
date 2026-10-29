package gourmet.cwaorecasting.hubapp.ui.composable.screen.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens

@Composable
fun DataEmptyContent(
    primaryText: String,
    modifier: Modifier = Modifier,
    secondaryText: String? = null,
) {
    val dimens = LocalDimens.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = primaryText,
            modifier = Modifier.padding(horizontal = dimens.screenPadding),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
        )

        secondaryText?.let {
            Spacer(modifier = Modifier.height(dimens.tightSpacing))

            Text(
                text = it,
                modifier = Modifier.padding(horizontal = dimens.screenPadding),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
