package gourmet.cwaorecasting.hubapp.ui.composable.screen.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import gourmet.cwaorecasting.hubapp.R
import gourmet.cwaorecasting.hubapp.ui.composable.approot.topbar.AppTopBar
import gourmet.cwaorecasting.hubapp.ui.composable.screen.about.card.AppInfoCard
import gourmet.cwaorecasting.hubapp.ui.composable.screen.about.rules.header.RulesHeader
import gourmet.cwaorecasting.hubapp.ui.composable.screen.about.rules.list.RulesList
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens
import gourmet.cwaorecasting.hubapp.ui.theme.cardBorderStroke

@Composable
fun GTENPAboutScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dimens = LocalDimens.current

    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(R.string.gtenp_about_title),
            onNavigateBack = onBackClick,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(dimens.screenPadding),
            verticalArrangement = Arrangement.spacedBy(dimens.sectionGap),
        ) {
            RulesCard(modifier = Modifier.fillMaxWidth())

            AppInfoCard(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun RulesCard(modifier: Modifier = Modifier) {
    val dimens = LocalDimens.current
    val rules = stringArrayResource(R.array.about_rules).toList()

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(dimens.cardCornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        border = cardBorderStroke(),
    ) {
        Column(
            modifier = Modifier.padding(dimens.cardPadding),
            verticalArrangement = Arrangement.spacedBy(dimens.sectionGap),
        ) {
            RulesHeader(title = stringResource(R.string.about_rules_title))
            RulesList(rules = rules)
        }
    }
}