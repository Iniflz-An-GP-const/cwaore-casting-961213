package gourmet.cwaorecasting.hubapp.guide.data.model

sealed class UrlState {
    data class Present(val url: String) : UrlState()

    object Loading : UrlState()
    object NoUrl : UrlState()
    object Denied : UrlState()

    companion object {
        const val ACCESS_DENIED_URL_VALUE = "access_denied"

        fun urlStateFromValue(url: String?): UrlState {
            return when (url) {
                null -> NoUrl
                ACCESS_DENIED_URL_VALUE -> Denied
                else -> Present(url)
            }
        }
    }
}