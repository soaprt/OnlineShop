package prt.sostrovsky.onlineshopapp.repository.fetcher

import prt.sostrovsky.onlineshopapp.service.response.ProductDTO

interface ProductsFetcher {
    suspend fun fetchProductBy(id: Int) : ProductDTO?
}