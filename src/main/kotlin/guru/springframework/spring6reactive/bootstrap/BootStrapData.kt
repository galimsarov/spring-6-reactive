package guru.springframework.spring6reactive.bootstrap

import guru.springframework.spring6reactive.domain.Beer
import guru.springframework.spring6reactive.domain.Customer
import guru.springframework.spring6reactive.repositories.BeerRepository
import guru.springframework.spring6reactive.repositories.CustomerRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime

@Component
@Suppress("unused")
class BootStrapData(private val beerRepository: BeerRepository, private val customerRepository: CustomerRepository) :
    CommandLineRunner {
    override fun run(vararg args: String?) {
        loadBeerData()
        beerRepository.count().subscribe { count -> println("Beer count is: $count") }
        loadCustomerData()
        customerRepository.count().subscribe { count -> println("Customer count is: $count") }
    }

    private fun loadBeerData() {
        beerRepository.count().subscribe { count ->
            if (count == 0L) {
                val beer1 = Beer(
                    beerName = "Galaxy Cat",
                    beerStyle = "Pale Ale",
                    upc = "12356",
                    price = BigDecimal("12.99"),
                    quantityOnHand = 122,
                    createdDate = LocalDateTime.now(),
                    lastModifiedDate = LocalDateTime.now()
                )
                val beer2 = Beer(
                    beerName = "Crank",
                    beerStyle = "Pale Ale",
                    upc = "12356222",
                    price = BigDecimal("11.99"),
                    quantityOnHand = 392,
                    createdDate = LocalDateTime.now(),
                    lastModifiedDate = LocalDateTime.now()
                )
                val beer3 = Beer(
                    beerName = "Sunshine City",
                    beerStyle = "IPA",
                    upc = "123456",
                    price = BigDecimal("13.99"),
                    quantityOnHand = 144,
                    createdDate = LocalDateTime.now(),
                    lastModifiedDate = LocalDateTime.now()
                )
                beerRepository.saveAll(listOf(beer1, beer2, beer3)).subscribe()
            }
        }
    }

    private fun loadCustomerData() {
        customerRepository.count().subscribe { count ->
            if (count == 0L) {
                val customer1 = Customer(
                    customerName = "John",
                    createdDate = LocalDateTime.now(),
                    lastModifiedDate = LocalDateTime.now()
                )
                val customer2 = Customer(
                    customerName = "Fiona",
                    createdDate = LocalDateTime.now(),
                    lastModifiedDate = LocalDateTime.now()
                )
                val customer3 = Customer(
                    customerName = "Sam",
                    createdDate = LocalDateTime.now(),
                    lastModifiedDate = LocalDateTime.now()
                )
                customerRepository.saveAll(listOf(customer1, customer2, customer3)).subscribe()
            }
        }
    }
}