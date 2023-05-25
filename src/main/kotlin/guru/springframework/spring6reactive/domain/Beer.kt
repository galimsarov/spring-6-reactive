package guru.springframework.spring6reactive.domain

import org.springframework.data.annotation.Id
import java.math.BigDecimal
import java.time.LocalDateTime

data class Beer(
    @Id
    var id: Int = 0,
    var beerName: String = "",
    var beerStyle: String = "",
    var upc: String = "",
    var quantityOnHand: Int = 0,
    var price: BigDecimal = BigDecimal(0),
    var createdDate: LocalDateTime = LocalDateTime.now(),
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),
)
