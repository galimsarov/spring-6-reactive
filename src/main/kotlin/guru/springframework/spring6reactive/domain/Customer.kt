package guru.springframework.spring6reactive.domain

import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class Customer(
    @Id
    var id: Int = 0,
    var customerName: String = "",
    var createdDate: LocalDateTime = LocalDateTime.now(),
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),
)
