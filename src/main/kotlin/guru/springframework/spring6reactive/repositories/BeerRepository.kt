package guru.springframework.spring6reactive.repositories

import guru.springframework.spring6reactive.domain.Beer
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface BeerRepository : ReactiveCrudRepository<Beer, Int>