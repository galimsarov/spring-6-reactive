package guru.springframework.spring6reactive.controllers

import guru.springframework.spring6reactive.model.BeerDTO
import guru.springframework.spring6reactive.services.BeerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
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
        return beerService.getBeerById(beerId).switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))
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
        return beerService.updateBeer(beerId, beerDTO)
            .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))
            .map { ResponseEntity.noContent().build() }
    }

    @PatchMapping(BEER_PATH_ID)
    fun updateExistingBeerPatchById(
        @PathVariable("beerId") beerId: Int,
        @RequestBody @Validated beerDTO: BeerDTO
    ): Mono<ResponseEntity<Unit>> {
        return beerService.patchBeer(beerId, beerDTO)
            .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))
            .map { ResponseEntity.ok().build() }
    }

    @DeleteMapping(BEER_PATH_ID)
    fun deleteBeer(@PathVariable("beerId") beerId: Int): Mono<ResponseEntity<Unit>> {
        return beerService.getBeerById(beerId)
            .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))
            .map { beerDto -> beerService.deleteBeerById(beerDto.id) }
            .thenReturn(ResponseEntity.noContent().build())
    }

    companion object {
        const val BEER_PATH = "/api/v2/beer"
        const val BEER_PATH_ID = "$BEER_PATH/{beerId}"
    }
}