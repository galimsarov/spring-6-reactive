package guru.springframework.spring6reactive.repositories

import guru.springframework.spring6reactive.domain.Customer

class CustomerRepositoryTest {
    companion object {
        fun getTestCustomer() = Customer(customerName = "Alex")
    }
}