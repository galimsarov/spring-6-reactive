package guru.springframework.spring6reactive.services

import guru.springframework.spring6reactive.model.BeerDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BeerService {
    fun listBeers(): Flux<BeerDTO>
    fun getBeerById(beerId: Int): Mono<BeerDTO>
    fun saveNewBeer(beerDTO: BeerDTO): Mono<BeerDTO>
    fun updateBeer(beerId: Int, beerDTO: BeerDTO): Mono<BeerDTO>
    fun patchBeer(beerId: Int, beerDTO: BeerDTO): Mono<BeerDTO>
    fun deleteBeerById(beerId: Int): Mono<Unit>
}