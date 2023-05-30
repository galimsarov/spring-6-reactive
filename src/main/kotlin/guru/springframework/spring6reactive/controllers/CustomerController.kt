package guru.springframework.spring6reactive.controllers

import guru.springframework.spring6reactive.model.CustomerDTO
import guru.springframework.spring6reactive.services.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@Suppress("unused")
class CustomerController(private val customerService: CustomerService) {
    @GetMapping(CUSTOMER_PATH)
    fun listCustomers(): Flux<CustomerDTO> {
        return customerService.listCustomers()
    }

    @GetMapping(CUSTOMER_PATH_ID)
    fun getCustomerById(@PathVariable("customerId") customerId: Int): Mono<CustomerDTO> {
        return customerService.getCustomerById(customerId)
    }

    @PostMapping(CUSTOMER_PATH)
    fun createNewCustomer(@RequestBody @Validated customerDTO: CustomerDTO): Mono<ResponseEntity<Unit>> {
        return customerService.saveNewCustomer(customerDTO).map { savedDto ->
            ResponseEntity.created(
                UriComponentsBuilder.fromHttpUrl("http://localhost:8080/$CUSTOMER_PATH/${savedDto.id}").build().toUri()
            ).build()
        }
    }

    @PutMapping(CUSTOMER_PATH_ID)
    fun updateExistingCustomer(
        @PathVariable("customerId") customerId: Int,
        @RequestBody @Validated customerDTO: CustomerDTO
    ): Mono<ResponseEntity<Unit>> {
        return customerService.updateCustomer(customerId, customerDTO).map { ResponseEntity.noContent().build() }
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    fun updateExistingCustomerPatchById(
        @PathVariable("customerId") customerId: Int,
        @RequestBody @Validated customerDTO: CustomerDTO
    ): Mono<ResponseEntity<Unit>> {
        return customerService.patchCustomer(customerId, customerDTO).map { ResponseEntity.ok().build() }
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    fun deleteCustomer(@PathVariable("customerId") customerId: Int): Mono<ResponseEntity<Unit>> {
        return customerService.deleteCustomerById(customerId).thenReturn(ResponseEntity.noContent().build())
    }

    companion object {
        const val CUSTOMER_PATH = "/api/v2/customer"
        const val CUSTOMER_PATH_ID = "$CUSTOMER_PATH/{customerId}"
    }
}