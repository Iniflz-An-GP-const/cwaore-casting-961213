package gourmet.cwaorecasting.hubapp.ui.composable.screen.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import gourmet.cwaorecasting.hubapp.R
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

private const val TITLE_START_DELAY_MS = 400L
private const val TAGLINE_START_DELAY_MS = 600L
private const val SPLASH_DURATION_MS = 1_200L
private const val SCALE_SPRING_STIFFNESS = 120f
private const val ALPHA_SPRING_STIFFNESS = 260f

private val LogoSize = 160.dp
private val RiseDistance = 12.dp
private val LogoCornerRadius = 40.dp

@Composable
fun SplashContent(
    modifier: Modifier = Modifier,
    onAnimationFinished: () -> Unit,
) {
    val dimens = LocalDimens.current

    val scale = remember { Animatable(0.6f) }
    val logoAlpha = remember { Animatable(0f) }
    val titleAlpha = remember { Animatable(0f) }
    val taglineAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch {
            launch {
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = SCALE_SPRING_STIFFNESS,
                    ),
                )
            }
            launch {
                logoAlpha.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = ALPHA_SPRING_STIFFNESS,
                    ),
                )
            }
            launch {
                delay(TITLE_START_DELAY_MS.milliseconds)
                titleAlpha.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = ALPHA_SPRING_STIFFNESS,
                    ),
                )
            }
            launch {
                delay(TAGLINE_START_DELAY_MS.milliseconds)
                taglineAlpha.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = ALPHA_SPRING_STIFFNESS,
                    ),
                )
            }
        }

        delay(SPLASH_DURATION_MS.milliseconds)
        onAnimationFinished()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(48.dp))
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.icon),
                contentDescription = stringResource(R.string.gtenp_app_logo_description),
                modifier = Modifier
                    .size(LogoSize)
                    .clip(RoundedCornerShape(LogoCornerRadius))
                    .scale(scale.value)
                    .alpha(logoAlpha.value),
            )

            Spacer(modifier = Modifier.height(dimens.contentSpacing))

            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.graphicsLayer {
                    alpha = titleAlpha.value
                    translationY = (1f - titleAlpha.value) * RiseDistance.toPx()
                },
            )

            Spacer(modifier = Modifier.height(dimens.tightSpacing))

            Text(
                text = stringResource(R.string.gtenp_main_menu_tagline),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.graphicsLayer {
                    alpha = taglineAlpha.value
                    translationY = (1f - taglineAlpha.value) * RiseDistance.toPx()
                },
            )
        }
    }
}
