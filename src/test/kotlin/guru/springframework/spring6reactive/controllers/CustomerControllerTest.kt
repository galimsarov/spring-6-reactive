package guru.springframework.spring6reactive.controllers

import guru.springframework.spring6reactive.mappers.toCustomerDto
import guru.springframework.spring6reactive.model.CustomerDTO
import guru.springframework.spring6reactive.repositories.CustomerRepositoryTest
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
class CustomerControllerTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Order(1)
    @Test
    fun testGetById() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .get().uri(CustomerController.CUSTOMER_PATH_ID, 1)
            .exchange()
            .expectStatus().isOk
            .expectHeader().valueEquals("Content-type", "application/json")
            .expectBody(CustomerDTO::class.java)
    }

    @Order(2)
    @Test
    fun testListCustomers() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .get().uri(CustomerController.CUSTOMER_PATH)
            .exchange()
            .expectStatus().isOk
            .expectHeader().valueEquals("Content-type", "application/json")
            .expectBody().jsonPath("$.size()").isEqualTo(3)
    }

    @Order(3)
    @Test
    fun testUpdateCustomer() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .put().uri(CustomerController.CUSTOMER_PATH_ID, 1)
            .body(Mono.just(CustomerRepositoryTest.getTestCustomer().toCustomerDto()), CustomerDTO::class.java)
            .exchange()
            .expectStatus().isNoContent
    }

    @Order(4)
    @Test
    fun testDeleteCustomer() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .delete().uri(CustomerController.CUSTOMER_PATH_ID, 1).exchange().expectStatus().isNoContent
    }

    @Order(5)
    @Test
    fun testCreateCustomer() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .post().uri(CustomerController.CUSTOMER_PATH)
            .body(Mono.just(CustomerRepositoryTest.getTestCustomer().toCustomerDto()), CustomerDTO::class.java)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isCreated
            .expectHeader().location("http://localhost:8080/api/v2/customer/4")
    }

    @Test
    fun testCreateCustomerBadData() {
        val testCustomer = CustomerRepositoryTest.getTestCustomer().apply { customerName = "" }

        webTestClient
            .mutateWith(mockOAuth2Login())
            .post().uri(CustomerController.CUSTOMER_PATH)
            .body(Mono.just(testCustomer.toCustomerDto()), CustomerDTO::class.java)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun testUpdateCustomerBadRequest() {
        val testCustomer = CustomerRepositoryTest.getTestCustomer().apply { customerName = "" }

        webTestClient
            .mutateWith(mockOAuth2Login())
            .put().uri(CustomerController.CUSTOMER_PATH_ID, 1)
            .body(Mono.just(testCustomer.toCustomerDto()), CustomerDTO::class.java)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun testGetByIdNotFound() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .get().uri(CustomerController.CUSTOMER_PATH_ID, 99)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun testUpdateCustomerNotFound() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .put().uri(CustomerController.CUSTOMER_PATH_ID, 999)
            .body(Mono.just(CustomerRepositoryTest.getTestCustomer().toCustomerDto()), CustomerDTO::class.java)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun testPatchCustomerNotFound() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .patch().uri(CustomerController.CUSTOMER_PATH_ID, 999)
            .body(Mono.just(CustomerRepositoryTest.getTestCustomer().toCustomerDto()), CustomerDTO::class.java)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun testDeleteNotFound() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .delete().uri(CustomerController.CUSTOMER_PATH_ID, 999).exchange().expectStatus().isNotFound
    }
}