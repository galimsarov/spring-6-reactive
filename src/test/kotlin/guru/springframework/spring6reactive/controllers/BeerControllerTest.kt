package guru.springframework.spring6reactive.controllers

import guru.springframework.spring6reactive.mappers.toBeerDto
import guru.springframework.spring6reactive.model.BeerDTO
import guru.springframework.spring6reactive.repositories.BeerRepositoryTest
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@AutoConfigureWebTestClient
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class BeerControllerTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Order(2)
    @Test
    fun testListBeers() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .get().uri(BeerController.BEER_PATH)
            .exchange()
            .expectStatus().isOk
            .expectHeader().valueEquals("Content-type", "application/json")
            .expectBody().jsonPath("$.size()").isEqualTo(3)
    }

    @Order(1)
    @Test
    fun testGetById() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .get().uri(BeerController.BEER_PATH_ID, 1)
            .exchange()
            .expectStatus().isOk
            .expectHeader().valueEquals("Content-type", "application/json")
            .expectBody(BeerDTO::class.java)
    }

    @Test
    fun testCreateBeer() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .post().uri(BeerController.BEER_PATH)
            .body(Mono.just(BeerRepositoryTest.getTestBeer().toBeerDto()), BeerDTO::class.java)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isCreated
            .expectHeader().location("http://localhost:8080/api/v2/beer/4")
    }

    @Order(3)
    @Test
    fun testUpdateBeer() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .put().uri(BeerController.BEER_PATH_ID, 1)
            .body(Mono.just(BeerRepositoryTest.getTestBeer().toBeerDto()), BeerDTO::class.java)
            .exchange()
            .expectStatus().isNoContent
    }

    @Order(999)
    @Test
    fun testDeleteBeer() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .delete().uri(BeerController.BEER_PATH_ID, 1).exchange().expectStatus().isNoContent
    }

    @Test
    fun testCreateBeerBadData() {
        val testBeer = BeerRepositoryTest.getTestBeer().apply { beerName = "" }

        webTestClient
            .mutateWith(mockOAuth2Login())
            .post().uri(BeerController.BEER_PATH)
            .body(Mono.just(testBeer.toBeerDto()), BeerDTO::class.java)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun testUpdateBeerBadRequest() {
        val testBeer = BeerRepositoryTest.getTestBeer().apply { beerStyle = "" }

        webTestClient
            .mutateWith(mockOAuth2Login())
            .put().uri(BeerController.BEER_PATH_ID, 1)
            .body(Mono.just(testBeer.toBeerDto()), BeerDTO::class.java)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun testGetByIdNotFound() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .get().uri(BeerController.BEER_PATH_ID, 99)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun testUpdateBeerNotFound() {
        webTestClient.mutateWith(mockOAuth2Login())
            .put().uri(BeerController.BEER_PATH_ID, 999)
            .body(Mono.just(BeerRepositoryTest.getTestBeer().toBeerDto()), BeerDTO::class.java)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun testPatchBeerNotFound() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .patch().uri(BeerController.BEER_PATH_ID, 999)
            .body(Mono.just(BeerRepositoryTest.getTestBeer().toBeerDto()), BeerDTO::class.java)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun testDeleteNotFound() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .delete().uri(BeerController.BEER_PATH_ID, 999).exchange().expectStatus().isNotFound
    }
}