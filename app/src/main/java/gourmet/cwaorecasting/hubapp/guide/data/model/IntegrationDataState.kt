package gourmet.cwaorecasting.hubapp.guide.data.model

data class IntegrationDataState(
    val urlState: UrlState = UrlState.Loading,
    val fcmToken: String? = null, //FIREBASE
) {

    val isReadyToInstall: Boolean
        get() = urlState is UrlState.NoUrl
                && fcmToken != null //FIREBASE

}
