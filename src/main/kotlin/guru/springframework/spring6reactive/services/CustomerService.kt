package guru.springframework.spring6reactive.services

import guru.springframework.spring6reactive.model.CustomerDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CustomerService {
    fun listCustomers(): Flux<CustomerDTO>
    fun getCustomerById(customerId: Int): Mono<CustomerDTO>
    fun saveNewCustomer(customerDTO: CustomerDTO): Mono<CustomerDTO>
    fun updateCustomer(customerId: Int, customerDTO: CustomerDTO): Mono<CustomerDTO>
    fun patchCustomer(customerId: Int, customerDTO: CustomerDTO): Mono<CustomerDTO>
    fun deleteCustomerById(customerId: Int): Mono<Unit>
}