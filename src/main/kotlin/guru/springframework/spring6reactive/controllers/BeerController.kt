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
    @GetMapping(beerPath)
    fun listBeers(): Flux<BeerDTO> {
        return beerService.listBeers()
    }

    @GetMapping(beerPathId)
    fun getBeerById(@PathVariable("beerId") beerId: Int): Mono<BeerDTO> {
        return beerService.getBeerById(beerId)
    }

    @PostMapping(beerPath)
    fun createNewBeer(@RequestBody @Validated beerDTO: BeerDTO): Mono<ResponseEntity<Unit>> {
        return beerService.saveNewBeer(beerDTO).map { savedDto ->
            ResponseEntity.created(
                UriComponentsBuilder.fromHttpUrl("http://localhost:8080/$beerPath/${savedDto.id}").build().toUri()
            ).build()
        }
    }

    @PutMapping(beerPathId)
    fun updateExistingBeer(
        @PathVariable("beerId") beerId: Int,
        @RequestBody @Validated beerDTO: BeerDTO
    ): Mono<ResponseEntity<Unit>> {
        return beerService.updateBeer(beerId, beerDTO).map { ResponseEntity.ok().build() }
    }

    @PatchMapping(beerPathId)
    fun updateExistingBeerPatchById(
        @PathVariable("beerId") beerId: Int,
        @RequestBody @Validated beerDTO: BeerDTO
    ): Mono<ResponseEntity<Unit>> {
        return beerService.patchBeer(beerId, beerDTO).map { ResponseEntity.ok().build() }
    }

    @DeleteMapping(beerPathId)
    fun deleteBeer(@PathVariable("beerId") beerId: Int): Mono<ResponseEntity<Unit>> {
        return beerService.deleteBeerById(beerId).map { ResponseEntity.noContent().build() }
    }

    companion object {
        const val beerPath = "/api/v2/beer"
        const val beerPathId = "$beerPath/{beerId}"
    }
}