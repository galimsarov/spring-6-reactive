package guru.springframework.spring6reactive.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import guru.springframework.spring6reactive.domain.Beer
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import java.math.BigDecimal

@DataR2dbcTest
class BeerRepositoryTest {
    @Autowired
    private lateinit var beerRepository: BeerRepository

    @Test
    fun saveNewBeer() {
        beerRepository.save(getTestBeer()).subscribe { beer -> println(beer.toString()) }
    }

    @Test
    fun testCreateJson() {
        val objectMapper = ObjectMapper().apply { registerModule(JavaTimeModule()) }

        println(objectMapper.writeValueAsString(getTestBeer()))
    }

    private fun getTestBeer() = Beer(
        beerName = "Space Dust",
        beerStyle = "IPA",
        price = BigDecimal.TEN,
        upc = "123213",
    )
}