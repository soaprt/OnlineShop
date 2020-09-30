package prt.sostrovsky.onlineshopapp.service.response

import prt.sostrovsky.onlineshopapp.domain.Product

data class ProductResponse(
    val id: Int,
    val title: String,
    val short_description: String,
    val image: String,
    val price: Int,
    val sale_precent: Int = 0,
    val details: String
)

fun ProductResponse.asDomainModel() : Product {
    return Product(
        id = id,
        title = title,
        shortDescription = short_description,
        imageUrl = image,
        price = price,
        salePercent = sale_precent,
        details = details
    )
}