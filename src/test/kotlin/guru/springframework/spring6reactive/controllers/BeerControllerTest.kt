package guru.springframework.spring6reactive.controllers

import guru.springframework.spring6reactive.mappers.toBeerDto
import guru.springframework.spring6reactive.model.BeerDTO
import guru.springframework.spring6reactive.repositories.BeerRepositoryTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun testListBeers() {
        webTestClient.get().uri(BeerController.BEER_PATH)
            .exchange()
            .expectStatus().isOk
            .expectHeader().valueEquals("Content-type", "application/json")
            .expectBody().jsonPath("$.size()").isEqualTo(3)
    }

    @Test
    fun testGetById() {
        webTestClient.get().uri(BeerController.BEER_PATH_ID, 1)
            .exchange()
            .expectStatus().isOk
            .expectHeader().valueEquals("Content-type", "application/json")
            .expectBody(BeerDTO::class.java)
    }

    @Test
    fun testCreateBeer() {
        webTestClient.post().uri(BeerController.BEER_PATH)
            .body(Mono.just(BeerRepositoryTest.getTestBeer().toBeerDto()), BeerDTO::class.java)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().location("http://localhost:8080/api/v2/beer/4")
    }
}