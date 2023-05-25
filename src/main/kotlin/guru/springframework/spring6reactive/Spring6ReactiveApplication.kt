package guru.springframework.spring6reactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Spring6ReactiveApplication

fun main(args: Array<String>) {
    runApplication<Spring6ReactiveApplication>(*args)
}
