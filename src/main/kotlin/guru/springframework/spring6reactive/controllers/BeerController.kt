package guru.springframework.spring6reactive.controllers

import guru.springframework.spring6reactive.model.BeerDTO
import guru.springframework.spring6reactive.services.BeerService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@Suppress("unused")
class BeerController(private val beerService: BeerService) {
    @GetMapping(BEER_PATH)
    fun listBeers(): Flux<BeerDTO> {
        return beerService.listBeers()
    }

    @GetMapping(BEER_PATH_ID)
    fun getBeerById(@PathVariable("beerId") beerId: Int): Mono<BeerDTO> {
        return beerService.getBeerById(beerId)
    }

    @PostMapping(BEER_PATH)
    fun createNewBeer(@RequestBody @Validated beerDTO: BeerDTO): Mono<ResponseEntity<Unit>> {
        return beerService.saveNewBeer(beerDTO).map { savedDto ->
            ResponseEntity.created(
                UriComponentsBuilder.fromHttpUrl("http://localhost:8080/$BEER_PATH/${savedDto.id}").build().toUri()
            ).build()
        }
    }

    @PutMapping(BEER_PATH_ID)
    fun updateExistingBeer(
        @PathVariable("beerId") beerId: Int,
        @RequestBody @Validated beerDTO: BeerDTO
    ): Mono<ResponseEntity<Unit>> {
        return beerService.updateBeer(beerId, beerDTO).map { ResponseEntity.noContent().build() }
    }

    @PatchMapping(BEER_PATH_ID)
    fun updateExistingBeerPatchById(
        @PathVariable("beerId") beerId: Int,
        @RequestBody @Validated beerDTO: BeerDTO
    ): Mono<ResponseEntity<Unit>> {
        return beerService.patchBeer(beerId, beerDTO).map { ResponseEntity.ok().build() }
    }

    @DeleteMapping(BEER_PATH_ID)
    fun deleteBeer(@PathVariable("beerId") beerId: Int): Mono<ResponseEntity<Unit>> {
        return beerService.deleteBeerById(beerId).thenReturn(ResponseEntity.noContent().build())
    }

    companion object {
        const val BEER_PATH = "/api/v2/beer"
        const val BEER_PATH_ID = "$BEER_PATH/{beerId}"
    }
}