package gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.start

data class StartBlockItem(
    val text: String,
    /** What is about to start, e.g. the chosen difficulty. */
    val subtitle: String? = null,
)
