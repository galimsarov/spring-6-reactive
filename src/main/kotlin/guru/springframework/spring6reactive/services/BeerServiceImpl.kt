package guru.springframework.spring6reactive.services

import guru.springframework.spring6reactive.mappers.toBeer
import guru.springframework.spring6reactive.mappers.toBeerDto
import guru.springframework.spring6reactive.model.BeerDTO
import guru.springframework.spring6reactive.repositories.BeerRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
@Suppress("unused")
class BeerServiceImpl(private val beerRepository: BeerRepository) : BeerService {
    override fun listBeers(): Flux<BeerDTO> {
        return beerRepository.findAll().map { it.toBeerDto() }
    }

    override fun getBeerById(beerId: Int): Mono<BeerDTO> {
        return beerRepository.findById(beerId).map { it.toBeerDto() }
    }

    override fun saveNewBeer(beerDTO: BeerDTO): Mono<BeerDTO> {
        return beerRepository.save(beerDTO.toBeer()).map { it.toBeerDto() }
    }

    override fun updateBeer(beerId: Int, beerDTO: BeerDTO): Mono<BeerDTO> {
        return beerRepository.findById(beerId)
            .map { foundBeer ->
                foundBeer.beerName = beerDTO.beerName
                foundBeer.beerStyle = beerDTO.beerStyle
                foundBeer.upc = beerDTO.upc
                foundBeer.quantityOnHand = beerDTO.quantityOnHand
                foundBeer.price = beerDTO.price
                foundBeer.lastModifiedDate = LocalDateTime.now()
                foundBeer
            }.flatMap { beerRepository.save(it) }.map { it.toBeerDto() }
    }

    override fun patchBeer(beerId: Int, beerDTO: BeerDTO): Mono<BeerDTO> {
        return beerRepository.findById(beerId)
            .map { foundBeer ->
                var modified = false
                if (beerDTO.beerName.isNotBlank()) {
                    foundBeer.beerName = beerDTO.beerName
                    modified = true
                }
                if (beerDTO.beerStyle.isNotBlank()) {
                    foundBeer.beerStyle = beerDTO.beerStyle
                    modified = true
                }
                if (beerDTO.upc.isNotBlank()) {
                    foundBeer.upc = beerDTO.upc
                    modified = true
                }
                if (beerDTO.quantityOnHand != 0) {
                    foundBeer.quantityOnHand = beerDTO.quantityOnHand
                    modified = true
                }
                if (beerDTO.price != BigDecimal(0)) {
                    foundBeer.price = beerDTO.price
                    modified = true
                }
                if (modified) {
                    foundBeer.lastModifiedDate = LocalDateTime.now()
                }
                foundBeer
            }.flatMap { beerRepository.save(it) }.map { it.toBeerDto() }
    }

    override fun deleteBeerById(beerId: Int): Mono<Unit> {
        return beerRepository.deleteById(beerId).map {}
    }
}