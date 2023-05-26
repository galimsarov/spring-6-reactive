package guru.springframework.spring6reactive.services

import guru.springframework.spring6reactive.mappers.toCustomer
import guru.springframework.spring6reactive.mappers.toCustomerDto
import guru.springframework.spring6reactive.model.CustomerDTO
import guru.springframework.spring6reactive.repositories.CustomerRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
@Suppress("unused")
class CustomerServiceImpl(private val customerRepository: CustomerRepository) : CustomerService {
    override fun listCustomers(): Flux<CustomerDTO> {
        return customerRepository.findAll().map { it.toCustomerDto() }
    }

    override fun getCustomerById(customerId: Int): Mono<CustomerDTO> {
        return customerRepository.findById(customerId).map { it.toCustomerDto() }
    }

    override fun saveNewCustomer(customerDTO: CustomerDTO): Mono<CustomerDTO> {
        return customerRepository.save(customerDTO.toCustomer()).map { it.toCustomerDto() }
    }

    override fun updateCustomer(customerId: Int, customerDTO: CustomerDTO): Mono<CustomerDTO> {
        return customerRepository.findById(customerId)
            .map { foundCustomer ->
                foundCustomer.customerName = customerDTO.customerName
                foundCustomer.lastModifiedDate = LocalDateTime.now()
                foundCustomer
            }.flatMap { customerRepository.save(it) }.map { it.toCustomerDto() }
    }

    override fun patchCustomer(customerId: Int, customerDTO: CustomerDTO): Mono<CustomerDTO> {
        return customerRepository.findById(customerId)
            .map { foundCustomer ->
                var modified = false
                if (customerDTO.customerName.isNotBlank()) {
                    foundCustomer.customerName = customerDTO.customerName
                    modified = true
                }
                if (modified) {
                    foundCustomer.lastModifiedDate = LocalDateTime.now()
                }
                foundCustomer
            }.flatMap { customerRepository.save(it) }.map { it.toCustomerDto() }
    }

    override fun deleteCustomerById(customerId: Int): Mono<Unit> {
        return customerRepository.deleteById(customerId).map {}
    }
}