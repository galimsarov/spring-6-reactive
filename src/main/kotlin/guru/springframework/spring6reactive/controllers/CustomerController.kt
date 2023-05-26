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
    @GetMapping(customerPath)
    fun listCustomers(): Flux<CustomerDTO> {
        return customerService.listCustomers()
    }

    @GetMapping(customerPathId)
    fun getCustomerById(@PathVariable("customerId") customerId: Int): Mono<CustomerDTO> {
        return customerService.getCustomerById(customerId)
    }

    @PostMapping(customerPath)
    fun createNewCustomer(@RequestBody @Validated customerDTO: CustomerDTO): Mono<ResponseEntity<Unit>> {
        return customerService.saveNewCustomer(customerDTO).map { savedDto ->
            ResponseEntity.created(
                UriComponentsBuilder.fromHttpUrl("http://localhost:8080/$customerPath/${savedDto.id}").build().toUri()
            ).build()
        }
    }

    @PutMapping(customerPathId)
    fun updateExistingCustomer(
        @PathVariable("customerId") customerId: Int,
        @RequestBody @Validated customerDTO: CustomerDTO
    ): Mono<ResponseEntity<Unit>> {
        return customerService.updateCustomer(customerId, customerDTO).map { ResponseEntity.ok().build() }
    }

    @PatchMapping(customerPathId)
    fun updateExistingCustomerPatchById(
        @PathVariable("customerId") customerId: Int,
        @RequestBody @Validated customerDTO: CustomerDTO
    ): Mono<ResponseEntity<Unit>> {
        return customerService.patchCustomer(customerId, customerDTO).map { ResponseEntity.ok().build() }
    }

    @DeleteMapping(customerPathId)
    fun deleteCustomer(@PathVariable("customerId") customerId: Int): Mono<ResponseEntity<Unit>> {
        return customerService.deleteCustomerById(customerId).map { ResponseEntity.noContent().build() }
    }

    companion object {
        const val customerPath = "/api/v2/customer"
        const val customerPathId = "$customerPath/{customerId}"
    }
}