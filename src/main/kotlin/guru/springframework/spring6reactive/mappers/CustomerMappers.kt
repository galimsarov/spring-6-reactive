package guru.springframework.spring6reactive.mappers

import guru.springframework.spring6reactive.domain.Customer
import guru.springframework.spring6reactive.model.CustomerDTO

fun Customer.toCustomerDto(): CustomerDTO = CustomerDTO(
    id = id,
    customerName = customerName,
    createdDate = createdDate,
    lastModifiedDate = lastModifiedDate
)

fun CustomerDTO.toCustomer(): Customer = Customer(
    id = id,
    customerName = customerName,
    createdDate = createdDate,
    lastModifiedDate = lastModifiedDate
)