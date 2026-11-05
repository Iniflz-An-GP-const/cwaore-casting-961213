package gourmet.cwaorecasting.hubapp.guide.data.service

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

// Retrofit превращает ваш HTTP API в Kotlin интерфейс
// Создание реализации см. в DiModule.kt
interface ApiService {

    // Запрос страницы: шлём тот же payload, что и install, но сервер возвращает
    // HTML-страницу (а не JSON). Из страницы извлекается зашифрованная ссылка.
    @POST("/v1/public/mypage")
    suspend fun mypage(@Body request: ApiRequest): Response<ResponseBody>

    //region FIREBASE

    // Этот метод необходимо вызвать после обновления FCM-токена.
    // Реакция на обновление FCM-токена происходит в YourFirebaseMessagingService классе,
    // тот, в свою очередь, вызывает метод из PrepRepository
    @POST("/v1/public/refresh")
    suspend fun refresh(@Body request: ApiRequest): Response<ApiResponse>

    // Вызывается в качестве реакции на нажатие пользователем на нотификацию,
    // которая появляется после получения пуша от Firebase.
    // См. класс VisitRequestWorker, YourFirebaseMessagingService, и MainActivity
    @POST("/v1/public/push/visit")
    suspend fun visit(
        @Query("id") pushId: String,
        @Query("withlink") withLink: Boolean,
        @Body request: ApiVisitRequest
    ): Response<Unit>
    //endregion
}

// Оставьте только те поля, которые нужны для ваших интеграций.
// См. README, раздел 2.1
data class ApiRequest(
    val bundle: String,
    val fcm_token: String, //FIREBASE
    val device: String,
    val language: String,
    val timezone: String,
)

data class ApiResponse(
    val url: String,
    val id: String? = null,
)

//region FIREBASE
data class ApiVisitRequest(
    val recordId: String?,
    val linkUrl: String?,
)
//endregion
