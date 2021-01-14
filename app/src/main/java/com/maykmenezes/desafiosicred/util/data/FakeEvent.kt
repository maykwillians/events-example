package com.maykmenezes.desafiosicred.util.data

import com.maykmenezes.desafiosicred.features.eventCheckIn.model.request.CheckInRequest
import com.maykmenezes.desafiosicred.features.eventDatails.model.response.EventDetailsResponse
import com.maykmenezes.desafiosicred.features.eventsList.model.response.EventItemResponse

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
val fakeEventList = listOf(fakeEvent, fakeEvent, fakeEvent, fakeEvent, fakeEvent, fakeEvent, fakeEvent)

val fakeEventDetails = EventDetailsResponse(
    date = 128743L,
    image = "https://mundodosmusicais.files.wordpress.com/2019/02/chicago-the-musical.jpg",
    price = 23.88,
    latitude = -10.85524874,
    description = "A produtora Stephanie Mayorkis, responsável por grandes produções brasileiras, como a primeira montagem de “O Fantasma da Ópera”, “O Rei Leão”, “Mamma Mia!” e “A Família Addams”, além dos sucessos de “My Fair Lady”, “Cantando na Chuva” e “A Pequena Sereia”, revelou à VEJA SP que já se prepara para 2020. No ano que vem, a produtora pretende trazer o musical “Chicago” de volta ao Brasil.",
    id = "2",
    title = "“CHICAGO” GANHARÁ NOVA PRODUÇÃO BRASILEIRA",
    longitude = -37.12609005
)

val fakeCheckIn = CheckInRequest(
    eventId = "2",
    name = "Mayk",
    email = "mayk@gmail.com"
)