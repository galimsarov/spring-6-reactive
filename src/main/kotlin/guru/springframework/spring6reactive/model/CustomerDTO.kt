package guru.springframework.spring6reactive.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class CustomerDTO(
    var id: Int = 0,

    @field:NotBlank
    @field:Size(max = 255)
    var customerName: String = "",
    var createdDate: LocalDateTime = LocalDateTime.now(),
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),
)
