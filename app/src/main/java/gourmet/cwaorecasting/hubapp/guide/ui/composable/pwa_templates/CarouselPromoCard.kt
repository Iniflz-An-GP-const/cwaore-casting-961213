package gourmet.cwaorecasting.hubapp.guide.ui.composable.pwa_templates

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gourmet.cwaorecasting.hubapp.R

private val poppinsFontFamily = FontFamily(
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
)

private val CardBackground = Color(0xFFF3F3F3)
private val CardText = Color(0xFF282828)
private val CardTextMuted = Color(0xFF888888)
private val CardButton = Color(0xFF14AC00)
private val BadgeLeftGradient = Color(0xFF0059FF)
private val BadgeRightGradient = Color(0xFF00B2FF)

@Composable
fun CarouselPromoCard(
    onButtonClick: () -> Unit,
    @DrawableRes iconRes: Int = R.drawable.carousel_card_icon,
    titleText: String = "Featured Slots",
    rate: Double = 4.4,
    subText: String = "Games",
    badgeText: String = "TRY TO WIN",
    buttonText: String = "PLAY",
) {
    Card(
        shape = RoundedCornerShape(21.6.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        modifier = Modifier
            .widthIn(max = 365.dp)
            .fillMaxWidth()
            .padding(horizontal = 17.6.dp)
            .padding(top = 14.4.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .padding(top = 16.dp)
                        .size(96.dp)
                        .clip(RoundedCornerShape(10.4.dp))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(
                        modifier = Modifier.height(18.4.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = titleText,
                            fontSize = 16.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = CardText
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val totalStars = 5
                        val filledStars = 4
                        repeat(totalStars) { index ->
                            val startPadding = if (index == 0) 0.dp else 2.dp
                            val endPadding = if (index == totalStars - 1) 0.dp else 2.dp

                            Image(
                                painter = painterResource(
                                    id = if (index < filledStars) R.drawable.star_selected
                                    else R.drawable.star_unselected
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(start = startPadding, end = endPadding)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = rate.toString(),
                            fontSize = 14.4.sp,
                            lineHeight = 16.85.sp,
                            fontFamily = poppinsFontFamily,
                            color = CardText,
                            fontWeight = FontWeight.Light
                        )
                    }

                    Spacer(modifier = Modifier.height(6.4.dp))

                    Text(
                        text = subText,
                        fontSize = 14.0.sp,
                        lineHeight = 16.38.sp,
                        fontFamily = poppinsFontFamily,
                        color = CardTextMuted,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Box(
                        modifier = Modifier
                            .padding(top = 6.4.dp, bottom = 5.5.dp)
                            .size(width = 113.6.dp, height = 20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            BadgeLeftGradient,
                                            BadgeRightGradient
                                        )
                                    ),
                                    shape = RoundedCornerShape(50)
                                )
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 8.8.dp),
                                text = badgeText,
                                color = Color.White,
                                fontSize = 9.6.sp,
                                lineHeight = 11.23.sp,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 0.sp
                            )
                        }

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .wrapContentSize(unbounded = true)
                                .offset(x = 10.2.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.carousel_badge_blur),
                                contentDescription = null,
                                modifier = Modifier.size(width = 45.16.dp, height = 48.05.dp)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.carousel_badge_icon),
                                contentDescription = null,
                                modifier = Modifier.size(width = 33.8.dp, height = 34.47.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onButtonClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CardButton,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(55.2.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 2.5.dp,
                    disabledElevation = 0.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 12.8.dp)
                    .height(48.8.dp)
            ) {
                Text(
                    text = buttonText,
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(
    name = "Carousel Promo Card",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    widthDp = 393,
    heightDp = 250
)
@Composable
private fun CarouselPromoCardPreview() {
    CarouselPromoCard(
        onButtonClick = {},
    )
}