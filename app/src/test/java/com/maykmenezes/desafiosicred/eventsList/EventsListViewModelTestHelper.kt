package com.maykmenezes.desafiosicred.eventsList

import com.maykmenezes.desafiosicred.features.eventsList.model.response.EventItemResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object EventsListViewModelTestHelper {

    val fakeEvent = EventItemResponse(
            date = 128743L,
            image = "https://mundodosmusicais.files.wordpress.com/2019/02/chicago-the-musical.jpg",
            price = 23.88,
            latitude = -10.85524874,
            description = "A produtora Stephanie Mayorkis, responsável por grandes produções brasileiras, como a primeira montagem de “O Fantasma da Ópera”, “O Rei Leão”, “Mamma Mia!” e “A Família Addams”, além dos sucessos de “My Fair Lady”, “Cantando na Chuva” e “A Pequena Sereia”, revelou à VEJA SP que já se prepara para 2020. No ano que vem, a produtora pretende trazer o musical “Chicago” de volta ao Brasil.",
            id = "2",
            title = "“CHICAGO” GANHARÁ NOVA PRODUÇÃO BRASILEIRA",
            longitude = -37.12609005
    )

    val successResponse: Response<List<EventItemResponse>> = Response.success(listOf(fakeEvent))

    val unknownErrorResponse: Response<List<EventItemResponse>> = Response.error(500, "".toResponseBody("".toMediaTypeOrNull()))
}