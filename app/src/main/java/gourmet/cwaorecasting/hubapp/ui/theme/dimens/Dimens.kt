package gourmet.cwaorecasting.hubapp.ui.theme.dimens

import androidx.compose.ui.unit.Dp

data class Dimens(
    /** Outer padding of the screen's root container. */
    val screenPadding: Dp,
    /** Spacing between semantic sections of the screen. */
    val sectionGap: Dp,
    /** Spacing between adjacent elements within a section. */
    val contentSpacing: Dp,
    /** Inner padding of a card. */
    val cardPadding: Dp,
    /** Spacing between small elements in a row — chips, an icon and text. */
    val itemSpacing: Dp,
    /** Minimum spacing between lines that are perceived as a single unit. */
    val tightSpacing: Dp,
    /** Corner radius of product cards and slides. */
    val cardCornerRadius: Dp,
    /** Corner radius of nested elements — thumbnails, previews, backgrounds. */
    val cornerRadius: Dp,
    /**
     * Corner radius of controls — chips and filter pills.
     *
     * A value greater than half the element's height is clipped to that height,
     * resulting in fully rounded ends. This allows the value to express a
     * "pill" shape without requiring a separate flag.
     */
    val controlCornerRadius: Dp,
    /** Border width of cards and slides. Zero means no border. */
    val cardBorderWidth: Dp,
    /** Border width of images inside cards. Zero means no border. */
    val imageBorderWidth: Dp,
    /** Border width of controls — chips and filter pills. */
    val controlBorderWidth: Dp,
)
