package guru.springframework.spring6reactive.mappers

import guru.springframework.spring6reactive.domain.Beer
import guru.springframework.spring6reactive.model.BeerDTO

fun Beer.toBeerDto(): BeerDTO = BeerDTO(
    id = id,
    beerName = beerName,
    beerStyle = beerStyle,
    upc = upc,
    quantityOnHand = quantityOnHand,
    price = price,
    createdDate = createdDate,
    lastModifiedDate = lastModifiedDate,
)

fun BeerDTO.toBeer(): Beer = Beer(
    id = id,
    beerName = beerName,
    beerStyle = beerStyle,
    upc = upc,
    quantityOnHand = quantityOnHand,
    price = price,
    createdDate = createdDate,
    lastModifiedDate = lastModifiedDate,
)